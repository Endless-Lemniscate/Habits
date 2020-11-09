package com.example.data.models.mappers

import com.example.data.models.RoomHabit
import com.example.data.models.RoomHabitDone
import com.example.domain.model.Habit
import com.example.domain.model.HabitDone


internal val RoomHabit.toHabit: Habit
    get() {
        val habit = Habit(
            name = this.name,
            description = this.description,
            date = this.date,
            count = this.count,
            period = this.period,
            type = this.type,
            priority = this.priority,
            color = this.color
        )
        habit.id = this.id
        return habit
    }

internal val RoomHabitDone.toHabitDone: HabitDone
    get() = HabitDone(
            habitId = this.habitId,
            date = this.date,
        )