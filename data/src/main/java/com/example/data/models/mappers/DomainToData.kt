package com.example.data.models.mappers

import com.example.data.models.RoomDoneDate
import com.example.data.models.RoomHabit
import com.example.domain.model.DoneDate
import com.example.domain.model.Habit


internal val Habit.toData: RoomHabit
    get() {
        val roomHabit = RoomHabit(
            id = this.id,
            name = this.name,
            description = this.description,
            date = this.date,
            count = this.count,
            period = this.period,
            type = this.type,
            priority = this.priority,
            color = this.color,
            status = this.status,
            remoteId = this.remoteId
        )

        return roomHabit
    }

internal val DoneDate.toData: RoomDoneDate
    get() = RoomDoneDate(this.id, this.habitId, this.date, this.status)
