package com.example.domain.usecases

import com.example.domain.model.Habit
import com.example.domain.repository.HabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


class DeleteHabitUseCase(private val habitRepository: HabitRepository,
                          private val dispatcher: CoroutineDispatcher) {

    suspend fun deleteHabit(habit: Habit) {
        return withContext(dispatcher) {
            habitRepository.deleteHabit(habit)
        }
    }

}