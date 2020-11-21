package com.example.domain.repository

import com.example.domain.model.Habit
import com.example.domain.model.Result
import kotlinx.coroutines.flow.Flow
import java.util.*


interface LocalHabitRepository {

    fun loadHabits(name: String = "", sort: Int = 0): Flow<List<Habit>>

    fun getNotSyncedAndDeleted(): Flow<List<Habit>>

    suspend fun insertHabit(habit: Habit): Result<Unit>

    suspend fun getHabitById(id: Int): Habit

    suspend fun deleteHabit(habit: Habit): Result<Unit>

    suspend fun insertDoneDate(id: Int, date: Date): Result<Unit>

    suspend fun setRemoteId(id: Int, remote_id: String)

    suspend fun syncedDelete(habit: Habit): Result<Unit>

    suspend fun isEmpty(): Boolean

}