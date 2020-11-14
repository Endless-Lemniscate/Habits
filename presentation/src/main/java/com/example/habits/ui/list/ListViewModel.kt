package com.example.habits.ui.list

import androidx.lifecycle.*
import com.example.domain.model.Habit
import com.example.domain.usecases.AccomplishHabitUseCase
import com.example.domain.usecases.DeleteHabitUseCase
import com.example.domain.usecases.LoadHabitsUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class ListViewModel(loadHabitsUseCase: LoadHabitsUseCase,
                    private val deleteHabitUseCase: DeleteHabitUseCase,
                    private val accomplishHabitUseCase: AccomplishHabitUseCase) : ViewModel() {

    val listHabits: LiveData<List<Habit>>
    private val firstFilter = MutableLiveData<String>("")
    private val secondFilter = MutableLiveData<Int>(0)

    private val mutableScrollState: MutableLiveData<Int> = MutableLiveData()
    val scrollState: LiveData<Int> = mutableScrollState

    private val combinedFilters = MediatorLiveData<Pair<String?, Int?>>().apply {
        addSource(firstFilter) {
            value = Pair(it, secondFilter.value)
        }
        addSource(secondFilter) {
            value = Pair(firstFilter.value, it)
        }
    }

    init {
        listHabits = Transformations.switchMap(combinedFilters) { pair ->
            val firstValue = pair.first
            val secondValue = pair.second
            if (firstValue != null && secondValue != null) {
                loadHabitsUseCase.loadHabitsWithFilters(firstValue, secondValue).asLiveData()
            } else null
        }
    }

    fun setNameFilter(name: String) {
        firstFilter.value = name
    }

    fun setSort(sort: Int) {
        //0 - DESC
        //1 - ASC
        secondFilter.value = sort
    }

    fun saveScrollState(scroll: Int) {
        mutableScrollState.value = scroll
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            deleteHabitUseCase.deleteHabit(habit)
        }
    }

    fun accomplishHabitAsync(habit: Habit) = viewModelScope.async {
        accomplishHabitUseCase.accomplishHabit(habit)
    }

}


