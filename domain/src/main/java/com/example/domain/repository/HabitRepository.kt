package com.example.domain.repository

import com.example.domain.model.Habit
import com.example.domain.model.HabitDone
import kotlinx.coroutines.flow.Flow


interface HabitRepository {
    fun getAllHabits(): Flow<List<Habit>>

    fun getAllHabitsWithFilters(name: String, sort: Int): Flow<List<Habit>>

    suspend fun insertHabit(habit: Habit)

    suspend fun getHabitById(id: Int): Habit

    suspend fun deleteHabit(habit: Habit)

    suspend fun insertHabitDone(habitDone: HabitDone)

    suspend fun getHabitDoneDates(habitId: Int): List<HabitDone>
}