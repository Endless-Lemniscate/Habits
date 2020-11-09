package com.example.domain.usecases

import com.example.domain.model.Habit
import com.example.domain.repository.HabitRepository
import kotlinx.coroutines.CoroutineDispatcher


class GetHabitByIdUseCase(private val habitRepository: HabitRepository,
                          private val dispatcher: CoroutineDispatcher) {

    suspend fun getHabitById(id: Int): Habit {
        return habitRepository.getHabitById(id)
    }

}