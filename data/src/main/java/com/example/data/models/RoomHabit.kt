package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.enums.HabitPeriod
import com.example.domain.model.enums.HabitPriority
import com.example.domain.model.enums.HabitType
import java.util.*


@Entity(tableName = "habit")
data class RoomHabit(
    var name: String,
    var description: String,
    var date: Date,
    var count: Int,
    var period: HabitPeriod,
    var type: HabitType,
    var priority: HabitPriority,
    var color: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}