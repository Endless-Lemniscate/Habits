package com.example.domain.usecases

import com.example.domain.model.Habit
import com.example.domain.repository.HabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


class InsertHabitUseCase(private val habitRepository: HabitRepository,
                         private val dispatcher: CoroutineDispatcher) {

    suspend fun insertHabit(habit: Habit) {
        return withContext(dispatcher) {
            habitRepository.insertHabit(habit)
        }
    }

}