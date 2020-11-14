package com.example.data.repository

import com.example.data.db.HabitDao
import com.example.data.models.mappers.*
import com.example.domain.model.Habit
import com.example.domain.model.HabitDone
import com.example.domain.repository.HabitRepository
import kotlinx.coroutines.flow.*


class HabitRepositoryImpl(private val habitDao: HabitDao): HabitRepository {

    override fun getAllHabits(): Flow<List<Habit>> {
        return habitDao.getAllHabits().map { it.map { item -> item.toHabit } }
    }

    override fun getAllHabitsWithFilters(name: String, sort: Int): Flow<List<Habit>> {
        return habitDao.getAllHabitsWithFilters(name, sort).map { it.map { item -> item.toHabit } }
    }

    override suspend fun insertHabit(habit: Habit) {
        habitDao.insertHabit(habit.toRoomHabit)
    }

    override suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit.toRoomHabit)
    }

    override suspend fun getHabitById(id: Int): Habit {
        return habitDao.getHabitById(id).toHabit
    }


    override suspend fun insertHabitDone(habitDone: HabitDone) {
        habitDao.insertHabitDone(habitDone.toRoomHabitDone)
    }

    override suspend fun getHabitDoneDates(habitId: Int): List<HabitDone> {
        return habitDao.getHabitDoneDates(habitId).map { it.toHabitDone }
    }

}