package com.example.data.models

import androidx.room.TypeConverter
import com.example.domain.model.enums.HabitPeriod
import com.example.domain.model.enums.HabitPriority
import com.example.domain.model.enums.HabitStatus
import com.example.domain.model.enums.HabitType
import java.util.*
import kotlin.collections.ArrayList


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

    //DoneDates
    @TypeConverter
    fun doneDatesToString(doneDates: ArrayList<Date>): String? {
        return doneDates.map { it.time }.joinToString()
    }
    @TypeConverter
    fun stringToDoneDates(string: String): ArrayList<Date>? {
        val list = ArrayList(string.split(", "))
        return try {
            ArrayList(list.map { Date(it.toLong()) })
        } catch (e: Exception) {
            arrayListOf<Date>()
        }
    }

    //HabitPeriod
    @TypeConverter
    fun statusToInt(status: HabitStatus): Int? {
        return status.ordinal
    }
    @TypeConverter
    fun intToStatus(ordinal: Int): HabitStatus? {
        return HabitStatus.values()[ordinal]
    }
}