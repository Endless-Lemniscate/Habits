package com.example.domain.usecases

import com.example.domain.model.Habit
import com.example.domain.model.Result
import com.example.domain.model.enums.HabitType
import com.example.domain.repository.LocalHabitRepository
import java.lang.Exception
import java.util.*


class AccomplishHabitUseCase(private val repository: LocalHabitRepository) {

    suspend fun accomplishHabit(habit: Habit): Result<String> {

        return try {
            //add habitDone
            repository.insertDoneDate(habit.id, Date())

            //get count of habits made in period
            val targetDate = Date().time - habit.period.duration.toMillis()
            val doneInPeriod = habit.doneDates.filter { it.time > targetDate }.size + 1

            return if (habit.type == HabitType.BAD) {
                if (doneInPeriod >= habit.count) {
                    Result.Success("Хватит это делать")
                } else {
                    Result.Success("можете выполнить еще ${habit.count - doneInPeriod} раза")
                }
            } else {
                if (doneInPeriod >= habit.count) {
                    Result.Success("You are breathtaking")
                } else {
                    Result.Success("стоит выполнить еще ${habit.count - doneInPeriod} раз")
                }
            }

        } catch (e: Exception) {
            Result.Error(e)
        }


    }

}