package com.example.data.models.mappers

import com.example.data.models.RoomHabit
import com.example.domain.model.Habit


internal val Habit.toRoomHabit: RoomHabit
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
            doneDates = this.doneDates,
            doneDatesNs = this.doneDatesNs,
            color = this.color,
            status = this.status,
            remoteId = this.remoteId
        )

        return roomHabit
    }