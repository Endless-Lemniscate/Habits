package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "habit_done")
data class RoomHabitDone(
    val habitId: Int,
    val date: Date
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}