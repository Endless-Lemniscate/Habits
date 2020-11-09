package com.example.domain.usecases

import com.example.domain.model.Habit
import com.example.domain.repository.HabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow


class LoadHabitsUseCase(private val habitRepository: HabitRepository,
                        private val dispatcher: CoroutineDispatcher) {

    fun loadHabits(): Flow<List<Habit>> {
        return habitRepository.getAllHabits()
    }

}