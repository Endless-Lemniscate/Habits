package com.example.habits.ui.habit_details


import androidx.databinding.InverseMethod
import com.example.domain.model.enums.HabitPeriod
import com.example.domain.model.enums.HabitPriority
import com.example.domain.model.enums.HabitType
import com.example.habits.R

object Converters {

    @InverseMethod("intToHabitType")
    @JvmStatic
    fun habitTypeToInt(habitType: HabitType?): Int {
        return when(habitType){
            HabitType.GOOD -> R.id.good
            else -> R.id.bad
        }
    }
    @JvmStatic
    fun intToHabitType(value: Int): HabitType {
        return when(value){
            R.id.good -> HabitType.GOOD
            else -> HabitType.BAD
        }
    }

    @InverseMethod("intToHabitPriority")
    @JvmStatic
    fun habitPriorityToInt(habitPriority: HabitPriority?): Int? {
        return habitPriority?.ordinal
    }
    @JvmStatic
    fun intToHabitPriority(ordinal: Int): HabitPriority {
        return HabitPriority.values()[ordinal]
    }

    @InverseMethod("stringToHabitPeriod")
    @JvmStatic
    fun habitPeriodToString(habitPeriod: HabitPeriod?): String? {
        return habitPeriod?.string
    }
    @JvmStatic
    fun stringToHabitPeriod(string: String?): HabitPeriod? {
        return HabitPeriod.values().find{ it.string == string }
    }

}