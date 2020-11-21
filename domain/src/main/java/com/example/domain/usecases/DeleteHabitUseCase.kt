package com.example.domain.usecases

import com.example.domain.model.Habit
import com.example.domain.model.Result
import com.example.domain.repository.LocalHabitRepository


class DeleteHabitUseCase(private val localHabitRepository: LocalHabitRepository) {
    suspend fun deleteHabit(habit: Habit): Result<Unit> {
        return localHabitRepository.deleteHabit(habit)
    }

    suspend fun syncedDeleteHabit(habit: Habit): Result<Unit> {
        return localHabitRepository.syncedDelete(habit)
    }
}