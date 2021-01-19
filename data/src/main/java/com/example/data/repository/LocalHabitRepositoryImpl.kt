package com.example.data.repository

import com.example.data.db.HabitDao
import com.example.data.models.mappers.*
import com.example.domain.model.DoneDate
import com.example.domain.model.Habit
import com.example.domain.model.Result
import com.example.domain.model.enums.EntityStatus
import com.example.domain.repository.LocalHabitRepository
import kotlinx.coroutines.flow.*


class LocalHabitRepositoryImpl(private val habitDao: HabitDao): LocalHabitRepository {

    override fun loadHabits(name: String, sort: Int): Flow<List<Habit>> {
        return habitDao.getAllHabitsWithFilters(name, sort)
            .map { it.map { item -> item.toDomain } }
    }

    override fun getNotSyncedOrDeleted(): Flow<List<Habit>> {
        return habitDao.getNotSyncedAndDeleted()
            .map { it.map { item -> item.toDomain } }
    }

    override suspend fun insertHabit(habit: Habit): Result<Unit> {
        return try {
            habitDao.insertHabit(habit.toData)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteHabit(habit: Habit): Result<Unit> {
        return try {
            habitDao.deleteHabit(habit.toData)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getHabitById(id: Int): Habit {
        return habitDao.getHabitById(id).toDomain
    }

    override suspend fun insertDoneDate(doneDate: DoneDate): Result<Unit> {
        return try {
            val roomDoneDate = doneDate.toData
            habitDao.insertDoneDate(roomDoneDate)

            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun setRemoteId(id: Int, remote_id: String) {
        habitDao.setRemoteId(id, remote_id)
    }

    override suspend fun syncedDelete(habit: Habit): Result<Unit> {
        return try {
            habitDao.syncedDeleteHabit(habit.id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun isEmpty(): Boolean {
        return habitDao.getAll().isEmpty()
    }

    override suspend fun setDateSynced(id: Int) {
        habitDao.setDateSynced(id)
    }

}

