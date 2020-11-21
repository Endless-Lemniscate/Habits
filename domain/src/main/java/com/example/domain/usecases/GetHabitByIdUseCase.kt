package com.example.domain.usecases

import com.example.domain.model.Habit
import com.example.domain.repository.LocalHabitRepository


class GetHabitByIdUseCase(private val localHabitRepository: LocalHabitRepository) {
    suspend fun getHabitById(id: Int): Habit {
        return localHabitRepository.getHabitById(id)
    }
}