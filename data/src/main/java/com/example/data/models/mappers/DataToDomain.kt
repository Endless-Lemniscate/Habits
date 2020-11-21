package com.example.data.models.mappers

import com.example.data.models.RoomHabit
import com.example.domain.model.Habit


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
            doneDates = this.doneDates,
            color = this.color,
            status = this.status,
        )

        habit.id = this.id
        habit.remoteId = this.remoteId
        habit.doneDatesNs = this.doneDatesNs

        return habit
    }