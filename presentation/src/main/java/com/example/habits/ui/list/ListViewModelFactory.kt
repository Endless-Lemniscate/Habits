package com.example.habits.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecases.AccomplishHabitUseCase
import com.example.domain.usecases.DeleteHabitUseCase
import com.example.domain.usecases.LoadHabitsUseCase
import com.example.domain.usecases.SyncHabitsWithRemoteUseCase

class ListViewModelFactory(private val loadHabitsUseCase: LoadHabitsUseCase,
                           private val deleteHabitUseCase: DeleteHabitUseCase,
                           private val accomplishHabitUseCase: AccomplishHabitUseCase,
                           private val syncHabitsWithRemoteUseCase: SyncHabitsWithRemoteUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            LoadHabitsUseCase::class.java,
            DeleteHabitUseCase::class.java,
            AccomplishHabitUseCase::class.java,
            SyncHabitsWithRemoteUseCase::class.java)
            .newInstance(loadHabitsUseCase, deleteHabitUseCase, accomplishHabitUseCase, syncHabitsWithRemoteUseCase)
    }
}
