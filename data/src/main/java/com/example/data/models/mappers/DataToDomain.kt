package com.example.data.models.mappers

import com.example.data.models.RoomDoneDate
import com.example.data.models.RoomHabitUnited
import com.example.domain.model.DoneDate
import com.example.domain.model.Habit


internal val RoomHabitUnited.toDomain: Habit
    get() {
        val habit = Habit(
            name = this.roomHabit.name,
            description = this.roomHabit.description,
            date = this.roomHabit.date,
            count = this.roomHabit.count,
            period = this.roomHabit.period,
            type = this.roomHabit.type,
            priority = this.roomHabit.priority,
            doneDates = this.doneDates.map { it.toDomain },
            color = this.roomHabit.color,
            status = this.roomHabit.status,
        )

        habit.id = this.roomHabit.id
        habit.remoteId = this.roomHabit.remoteId

        return habit
    }

internal val RoomDoneDate.toDomain: DoneDate
    get() {
        val doneDate = DoneDate(this.date, this.status)
        doneDate.id = this.id
        doneDate.habitId = this.habitId

        return doneDate
    }
