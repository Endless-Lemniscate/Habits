package com.example.domain.repository

import com.example.domain.model.Habit
import com.example.domain.model.Result
import java.util.*


interface RemoteHabitRepository {

    suspend fun getHabits(): Result<List<Habit>>

    suspend fun insertHabit(habit: Habit): Result<String>

    suspend fun deleteHabit(uid: String): Result<Unit>

    suspend fun habitDone(uid: String, date: Date): Result<Unit>

}