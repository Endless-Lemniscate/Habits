package com.example.domain.usecases

import com.example.domain.model.Habit
import com.example.domain.model.Result
import com.example.domain.model.enums.HabitStatus
import com.example.domain.repository.LocalHabitRepository
import java.util.*


class InsertHabitUseCase(private val localHabitRepository: LocalHabitRepository) {

    suspend fun insertHabit(habit: Habit): Result<Unit> {

        if (habit.name == "") {
            habit.name = "Без названия"
        }
        if (habit.description == "") {
            habit.description = "без описания"
        }
        habit.date = Date()
        habit.status = HabitStatus.NOT_SYNCED
        return localHabitRepository.insertHabit(habit)

    }


}