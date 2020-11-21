package com.example.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.enums.HabitPeriod
import com.example.domain.model.enums.HabitPriority
import com.example.domain.model.enums.HabitStatus
import com.example.domain.model.enums.HabitType
import java.util.*
import kotlin.collections.ArrayList


@Entity(tableName = "habit")
data class RoomHabit(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var description: String,
    var date: Date,
    var count: Int,
    var period: HabitPeriod,
    var type: HabitType,
    var priority: HabitPriority,
    var doneDates: ArrayList<Date>,
    var doneDatesNs: ArrayList<Date>,
    var color: Int,
    var status: HabitStatus, //status 0 -> nothing, //status 1 -> notSynced, status 2 -> deleted
    var remoteId: String?
)