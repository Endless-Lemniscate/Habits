package com.example.habits.di.components

import com.example.domain.usecases.*
import com.example.habits.di.modules.ContextModule
import com.example.habits.di.modules.HabitsModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [HabitsModule::class, ContextModule::class])
interface ApplicationComponent {
    fun getDeleteHabitUseCase(): DeleteHabitUseCase

    fun getLoadHabitsUseCase(): LoadHabitsUseCase

    fun getGetHabitByIdUseCase(): GetHabitByIdUseCase

    fun getInsertHabitUseCase(): InsertHabitUseCase

    fun getAccomplishHabitUseCase(): AccomplishHabitUseCase
}