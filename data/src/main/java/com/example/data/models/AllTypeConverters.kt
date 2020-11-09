package com.example.data.models

import androidx.room.TypeConverter
import com.example.domain.enums.HabitPeriod
import com.example.domain.enums.HabitPriority
import com.example.domain.enums.HabitType
import java.util.*


class AllTypeConverters {
    //Date
    @TypeConverter
    fun dateToLong(date: Date?): Long? {
        return date?.time
    }
    @TypeConverter
    fun longToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    //HabitType
    @TypeConverter
    fun typeToInt(type: HabitType?): Int? {
        return type?.ordinal
    }
    @TypeConverter
    fun intToType(ordinal: Int): HabitType? {
        return HabitType.values()[ordinal]
    }

    //HabitPriority
    @TypeConverter
    fun priorityToInt(priority: HabitPriority?): Int? {
        return priority?.ordinal
    }
    @TypeConverter
    fun intToPriority(ordinal: Int): HabitPriority? {
        return HabitPriority.values()[ordinal]
    }

    //HabitPeriod
    @TypeConverter
    fun periodToInt(period: HabitPeriod): Int? {
        return period.ordinal
    }
    @TypeConverter
    fun intToPeriod(ordinal: Int): HabitPeriod? {
        return HabitPeriod.values()[ordinal]
    }
}