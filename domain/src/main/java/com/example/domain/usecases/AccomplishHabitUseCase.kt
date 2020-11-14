package com.example.domain.usecases

import com.example.domain.model.Habit
import com.example.domain.model.HabitDone
import com.example.domain.model.enums.HabitType
import com.example.domain.repository.HabitRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*


class AccomplishHabitUseCase(private val habitRepository: HabitRepository,
                             private val dispatcher: CoroutineDispatcher
) {

    suspend fun accomplishHabit(habit: Habit): String {

        //add habitDone
        val habitDone = HabitDone(habit.id, Date())
        habitRepository.insertHabitDone(habitDone)

        //get count of habits made in period
        val targetDateLong = Date().time - habit.period.duration.toMillis()
        val targetDate = Date(targetDateLong)
        val allHabitDone = habitRepository.getHabitDoneDates(habit.id)
        val doneInPeriod = allHabitDone.filter { it.date > targetDate}.size

        return if(habit.type == HabitType.BAD){
            if(doneInPeriod >= habit.count) {
                "Хватит это делать"
            } else {
                "можете выполнить еще ${habit.count - doneInPeriod} раза"
            }
        } else {
            if(doneInPeriod >= habit.count) {
                "You are breathtaking"
            } else {
                "стоит выполнить еще ${habit.count - doneInPeriod} раз"
            }
        }

    }

}