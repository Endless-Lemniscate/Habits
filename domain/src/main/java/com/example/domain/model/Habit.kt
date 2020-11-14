package com.example.domain.model

import com.example.domain.model.enums.HabitPeriod
import com.example.domain.model.enums.HabitPriority
import com.example.domain.model.enums.HabitType
import java.util.*


data class Habit (
    var name: String,
    var description: String,
    var date: Date,
    var count: Int,
    var period: HabitPeriod,
    var type: HabitType,
    var priority: HabitPriority,
    var color: Int,
) {
    var id: Int = 0
}


