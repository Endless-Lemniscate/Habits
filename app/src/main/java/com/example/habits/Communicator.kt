package com.example.habits

import android.os.Bundle
import android.view.View
import com.example.habits.models.Habit

interface Communicator {
    fun passDataToHabit(bundle: Bundle, elem: View)
    fun returnHabitToList(habit: Habit, isNew: Boolean, index: Int, view: View)
}