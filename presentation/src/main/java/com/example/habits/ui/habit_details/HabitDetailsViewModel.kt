package com.example.habits.ui.habit_details

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Habit
import com.example.domain.model.Result
import com.example.domain.model.enums.HabitPeriod
import com.example.domain.model.enums.HabitPriority
import com.example.domain.model.enums.HabitStatus
import com.example.domain.model.enums.HabitType
import com.example.domain.usecases.InsertHabitUseCase
import com.example.domain.usecases.GetHabitByIdUseCase
import kotlinx.coroutines.launch
import java.util.*


class HabitDetailsViewModel(private val getHabitByIdUseCase: GetHabitByIdUseCase,
                            private val addHabitUseCase: InsertHabitUseCase,
                            private val id: Int?) : ViewModel() {

    private val mutableHabit: MutableLiveData<Habit> = MutableLiveData()
    private val mutableLoaded: MutableLiveData<Boolean> = MutableLiveData()
    private val mutableColorPickerIsShowed: MutableLiveData<Boolean> = MutableLiveData()

    val habit: LiveData<Habit> = mutableHabit
    val loaded: LiveData<Boolean> = mutableLoaded
    val colorPickerIsShowed: LiveData<Boolean> = mutableColorPickerIsShowed

    init {
        getHabit()
        mutableColorPickerIsShowed.value = false
    }

    //get habit instance from db or create new
    private fun getHabit(){
        id?.let{
            mutableLoaded.value = false
            viewModelScope.launch {
                mutableHabit.value = getHabitByIdUseCase.getHabitById(it)
                mutableLoaded.value = true
            }
        } ?: run {
            mutableHabit.value = Habit("", "", Date(),
                1, HabitPeriod.HOUR, HabitType.GOOD, HabitPriority.HIGH, arrayListOf(), Color.RED, HabitStatus.NOT_SYNCED)
            mutableLoaded.value = true
        }
    }

    fun showColorPicker() {
        mutableColorPickerIsShowed.value = true
    }

    fun hideColorPicker() {
        mutableColorPickerIsShowed.value = false
    }

    fun setColorToColorShowingElement(color: Int) {
        val habit = mutableHabit.value
        habit?.color = color
        mutableHabit.value = habit
    }

    suspend fun insertHabitToDB(): String {
        val result = addHabitUseCase.insertHabit(habit.value!!)

        return when(result) {
            is Result.Success -> "Успех"
            is Result.Error -> "Ошибка"
        }
    }

    fun getRgbString() : String {
        val rgb = Color.valueOf(mutableHabit.value!!.color)
        return "RGB(${(rgb.red()*255).toInt()}, ${(rgb.green()*255).toInt()}, ${(rgb.blue()*255).toInt()})"
    }

    fun getHsvString() : String {
        val hsv = FloatArray(3)
        Color.colorToHSV(mutableHabit.value!!.color, hsv)
        return "HSV(${hsv[0].toInt()}, ${hsv[1]}, ${hsv[2]})"
    }

}