package com.example.data.models.mappers

import com.example.data.models.RoomHabit
import com.example.data.models.RoomHabitDone
import com.example.domain.model.Habit
import com.example.domain.model.HabitDone


internal val Habit.toRoomHabit: RoomHabit
    get() {
        val roomHabit = RoomHabit(
            name = this.name,
            description = this.description,
            date = this.date,
            count = this.count,
            period = this.period,
            type = this.type,
            priority = this.priority,
            color = this.color
        )
        roomHabit.id = this.id
        return roomHabit
    }

internal val HabitDone.toRoomHabitDone: RoomHabitDone
    get() = RoomHabitDone(
        habitId = this.habitId,
        date = this.date,
    )