package com.example.domain.usecases

import com.example.domain.model.Habit
import com.example.domain.model.Result
import com.example.domain.model.enums.HabitStatus
import com.example.domain.repository.LocalHabitRepository
import com.example.domain.repository.RemoteHabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.util.*


class SyncHabitsWithRemoteUseCase(private val local: LocalHabitRepository, private val remote: RemoteHabitRepository) {

     fun run(): Flow<SyncStatus<Int>> {
         val queue = local.getNotSyncedAndDeleted()

         val flow = flow {
             if (local.isEmpty()) {
                 val status = getAllFromRemote()
                 emit(status)
             }

             queue.collect { habits ->

                if (habits.isEmpty()) emit(SyncStatus.Success)
                else {
                    val status = Sync(habits[0]).run()
                    if(status is SyncStatus.Error) {
                        emit(SyncStatus.Error(status.error))
                    }
                    else {
                        emit(SyncStatus.InProgress(habits.size))
                    }
                }
                kotlinx.coroutines.delay(1000)
            }

        }
         return flow

    }


    private inner class Sync(private val habit: Habit) {

        suspend fun run(): SyncStatus<Int> {
            return when (habit.status) {
                HabitStatus.NOT_SYNCED -> syncWithRemote(habit)
                HabitStatus.DELETED -> deleteRemoteAndLocal(habit)
                else -> SyncStatus.Error(Exception())
            }
        }

        private suspend fun syncWithRemote(habit: Habit): SyncStatus<Int> {
            habit.date = Date()
            return when (val result = remote.insertHabit(habit)) {
                is Result.Error -> SyncStatus.Error(result.error)
                is Result.Success -> {
                    val id = result.data
                    habit.doneDatesNs.forEach { remote.habitDone(id, it) }
                    habit.apply {
                        doneDatesNs = arrayListOf()
                        remoteId = id
                        status = HabitStatus.OK
                    }
                    local.insertHabit(habit)
                    SyncStatus.Success
                }
            }
        }

        private suspend fun deleteRemoteAndLocal(habit: Habit): SyncStatus<Int> {
            return habit.remoteId?.let {
                return@let when (val result = remote.deleteHabit(it)) {
                    is Result.Error -> SyncStatus.Error(result.error)
                    is Result.Success -> {
                        local.deleteHabit(habit)
                        SyncStatus.Success
                    }
                }
            } ?: SyncStatus.Error(Exception())
        }
    }


    private suspend fun getAllFromRemote(): SyncStatus<Int> {

        return when(val result = remote.getHabits()) {
            is Result.Error -> SyncStatus.Error(result.error)
            is Result.Success -> {
                val remoteHabits = result.data
                remoteHabits.forEach {
                    local.insertHabit(it)
                }
                SyncStatus.Success
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