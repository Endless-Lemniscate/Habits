package com.example.habits.ui.list

import androidx.lifecycle.*
import com.example.domain.model.Habit
import com.example.domain.model.Result
import com.example.domain.usecases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ListViewModel(loadHabitsUseCase: LoadHabitsUseCase,
                    private val deleteHabitUseCase: DeleteHabitUseCase,
                    private val accomplishHabitUseCase: AccomplishHabitUseCase,
                    syncHabitsWithRemoteUseCase: SyncHabitsWithRemoteUseCase) : ViewModel() {

    val syncStatus: LiveData<SyncStatus<Int>> = syncHabitsWithRemoteUseCase.run().asLiveData()
    val listHabits: LiveData<List<Habit>>
    private val firstFilter = MutableLiveData("")
    private val secondFilter = MutableLiveData(0)

    private val mutableScrollState: MutableLiveData<Int> = MutableLiveData()
    val scrollState: LiveData<Int> = mutableScrollState

    private val mutableToastMessage: MutableLiveData<String> = MutableLiveData()
    val toastMessage: LiveData<String> = mutableToastMessage

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
                loadHabitsUseCase.loadHabits(firstValue, secondValue).asLiveData()
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
        viewModelScope.launch(Dispatchers.IO) {
            val result = deleteHabitUseCase.syncedDeleteHabit(habit)
            when(result) {
                is Result.Success -> showToast("Привычка успешно удалена")
                is Result.Error -> showToast("Ошибка: ${result.error}")
            }
        }
    }

    fun accomplishHabit(habit: Habit) {
        viewModelScope.launch {
            val result = accomplishHabitUseCase.accomplishHabit(habit)
            when(result) {
                is Result.Success -> showToast(result.data)
                is Result.Error -> showToast("Ошибка: ${result.error}")
            }
        }
    }

    private fun showToast(message: String) {
        mutableToastMessage.postValue(message)
    }

}


