package com.example.habits.ui.list

import androidx.lifecycle.*
import com.example.domain.model.Habit
import com.example.domain.usecases.AccomplishHabitUseCase
import com.example.domain.usecases.DeleteHabitUseCase
import com.example.domain.usecases.LoadHabitsUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ListViewModel(loadHabitsUseCase: LoadHabitsUseCase,
                    private val deleteHabitUseCase: DeleteHabitUseCase,
                    private val accomplishHabitUseCase: AccomplishHabitUseCase) : ViewModel() {

    //private val mutableListHabits: MutableLiveData<List<Habit>> = MutableLiveData()
    val listHabits: LiveData<List<Habit>> = loadHabitsUseCase.loadHabits().asLiveData()

    private val mutableScrollState: MutableLiveData<Int> = MutableLiveData()
    val scrollState: LiveData<Int> = mutableScrollState

    fun saveScrollState(scroll: Int){
        mutableScrollState.value = scroll
    }

    fun deleteHabit(habit: Habit){
        viewModelScope.launch {
            deleteHabitUseCase.deleteHabit(habit)
        }
    }

    fun accomplishHabitAsync(habit: Habit) = GlobalScope.async {
        accomplishHabitUseCase.accomplishHabit(habit)
    }

}

//    private val allItemsFiltered: LiveData<List<Habit>>
//    private val filter = MutableLiveData<Array<String>>(arrayOf("%", "0"))

//    init {
//        val habitDao = AppRoomDatabase.getDatabase(application).habitDao()
//        repository = HabitRepository(habitDao)
//
//        allItemsFiltered = Transformations.switchMap(filter) { filter ->
//            repository.getItemsFiltered(filter[0], filter[1].toInt())
//        }
//    }

//    fun getAllFiltered() : LiveData<List<Habit>>{
//        return allItemsFiltered
//    }
//
//    fun setFilter(filterName: String, type: Int) {
//        var name = "%"
//        if(filterName != ""){
//            name = "%$filterName%"
//        }
//        val newFilter: Array<String> = arrayOf(name, type.toString())
//        filter.postValue(newFilter)
//    }
//
//    fun deleteHabit(habit: Habit) = viewModelScope.launch(Dispatchers.IO) {
//        repository.delete(habit)
//    }




