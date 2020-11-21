package com.example.domain.usecases

import com.example.domain.model.Habit
import com.example.domain.repository.LocalHabitRepository
import kotlinx.coroutines.flow.Flow


class LoadHabitsUseCase(private val localHabitRepository: LocalHabitRepository) {

   fun loadHabits(name: String, sort: Int): Flow<List<Habit>> {
        val nameFilter = when (name) {
            "" -> "%"
            else -> "%$name%"
        }
        return localHabitRepository.loadHabits(nameFilter, sort)
   }

}