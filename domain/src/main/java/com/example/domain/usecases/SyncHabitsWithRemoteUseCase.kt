package com.example.domain.usecases

import com.example.domain.model.Habit
import com.example.domain.model.Result
import com.example.domain.model.enums.EntityStatus
import com.example.domain.repository.LocalHabitRepository
import com.example.domain.repository.RemoteHabitRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import java.util.*


const val REQUEST_EVERY = 1000L

class SyncHabitsWithRemoteUseCase(private val local: LocalHabitRepository, private val remote: RemoteHabitRepository) {

     fun run(): Flow<SyncStatus<Int>> = flow {

         if (local.isEmpty()) {
             when (val result = HabitSyncManager().getAllFromRemote()) {
                 is Result.Success -> emit(SyncStatus.Success)
                 is Result.Error -> emit(SyncStatus.Error(result.error))
             }
         }

         local.getNotSyncedOrDeleted().collect { habits ->

             //if list is empty then return success
             val status = if (habits.isEmpty()) {
                 SyncStatus.Success
             }
             //else do sync
             else {
                 when (val result = HabitSyncManager().syncHabit(habits[0])) {
                     is Result.Success -> SyncStatus.InProgress(habits.size)
                     is Result.Error -> {
                         when(result.error) {
                             is UnknownHostException -> SyncStatus.Offline
                             else -> SyncStatus.Error(result.error)
                         }
                     }
                 }
             }
             emit(status)
             delay(REQUEST_EVERY)
        }

    }

    private inner class HabitSyncManager {

        suspend fun syncHabit(habit: Habit): Result<Unit> =
            when (habit.status) {
                EntityStatus.NOT_SYNCED -> updateRemote(habit)
                EntityStatus.DELETED -> deleteRemoteAndLocal(habit)
                EntityStatus.OK -> Result.Error(Exception())
        }

        private suspend fun updateRemote(habit: Habit): Result<Unit> {
            habit.date = Date()
            return when (val result = remote.insertHabit(habit)) {
                is Result.Error -> Result.Error(result.error)
                is Result.Success -> {
                    val id = result.data

                    habit.doneDates.forEach { doneDates ->
                        if(doneDates.status == EntityStatus.NOT_SYNCED) {
                            remote.habitDone(id, doneDates.date)
                            local.setDateSynced(doneDates.id)
                        }
                    }
                    habit.status = EntityStatus.OK
                    local.insertHabit(habit)
                    Result.Success(Unit)
                }
            }
        }

        private suspend fun deleteRemoteAndLocal(habit: Habit): Result<Unit> {
            return habit.remoteId?.let {
                return@let when (val result = remote.deleteHabit(it)) {
                    is Result.Error -> Result.Error(result.error)
                    is Result.Success -> {
                        local.deleteHabit(habit)
                        Result.Success(Unit)
                    }
                }
            } ?: Result.Error(Exception())
        }

        suspend fun getAllFromRemote(): Result<Unit> =
            when(val result = remote.getHabits()) {
                is Result.Error -> Result.Error(result.error)
                is Result.Success -> {
                    val remoteHabits = result.data
                    remoteHabits.forEach {habit ->
                        local.insertHabit(habit)
                        habit.doneDates.forEach { doneDate ->
                            doneDate.habitId = habit.id
                            local.insertDoneDate(doneDate)
                        }
                    }
                    Result.Success(Unit)
                }
            }
    }


//    private suspend fun deleteAllFromRemote() {
//         val result = remote.getHabits()
//         if(result is Result.Success) {
//             val habits = result.data
//             habits.forEach {
//                 remote.deleteHabit(it.remoteId!!)
//             }
//         }
//    }


}







sealed class SyncStatus<out T: Any> {
    data class InProgress(val remain: Int) : SyncStatus<Int>()
    data class Error(val error: Exception) : SyncStatus<Nothing>()
    object Offline: SyncStatus<Nothing>()
    object Success: SyncStatus<Nothing>()
}