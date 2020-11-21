package com.example.data.repository

import com.example.data.db.HabitDao
import com.example.data.models.mappers.*
import com.example.domain.model.Habit
import com.example.domain.model.Result
import com.example.domain.model.enums.HabitStatus
import com.example.domain.repository.LocalHabitRepository
import kotlinx.coroutines.flow.*
import java.util.*


class LocalHabitRepositoryImpl(private val habitDao: HabitDao): LocalHabitRepository {

    override fun loadHabits(name: String, sort: Int): Flow<List<Habit>> {
        return habitDao.getAllHabitsWithFilters(name, sort)
            .map { it.map { item -> item.toHabit } }
    }

    override fun getNotSyncedAndDeleted(): Flow<List<Habit>> {
        return habitDao.getNotSyncedAndDeleted()
            .map { it.map { item -> item.toHabit } }
    }

    override suspend fun insertHabit(habit: Habit): Result<Unit> {
        return try {
            habitDao.insertHabit(habit.toRoomHabit)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteHabit(habit: Habit): Result<Unit> {
        return try {
            habitDao.deleteHabit(habit.toRoomHabit)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getHabitById(id: Int): Habit {
        return habitDao.getHabitById(id).toHabit
    }

    override suspend fun insertDoneDate(id: Int, date: Date): Result<Unit> {
        return try {
            val habit = habitDao.getHabitById(id)
            habit.doneDates.add(date)
            habit.doneDatesNs.add(date) //done dates not synced
            habit.status = HabitStatus.NOT_SYNCED
            habitDao.insertHabit(habit)
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

}

