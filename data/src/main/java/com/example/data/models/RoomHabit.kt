package com.example.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.domain.model.enums.HabitPeriod
import com.example.domain.model.enums.HabitPriority
import com.example.domain.model.enums.EntityStatus
import com.example.domain.model.enums.HabitType
import java.util.*


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
    var color: Int,
    var status: EntityStatus,
    var remoteId: String?
)

@Entity(tableName = "done_dates")
data class RoomDoneDate(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val habitId: Int,
    val date: Date,
    val status: EntityStatus // OK or NOT_SYNCED
)


data class RoomHabitUnited(
    @Embedded
    val roomHabit: RoomHabit,
    @Relation(
        parentColumn = "id",
        entityColumn = "habitId"
    )
    val doneDates: List<RoomDoneDate>
)