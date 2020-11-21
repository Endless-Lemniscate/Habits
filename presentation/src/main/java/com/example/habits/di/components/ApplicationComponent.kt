package com.example.habits.di.components

import com.bumptech.glide.RequestManager
import com.example.domain.usecases.*
import com.example.habits.di.modules.*
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [UseCasesModule::class,
    ContextModule::class,
    LocalHabitRepositoryModule::class,
    RemoteHabitRepositoryModule::class,
    GlideModule::class])
interface ApplicationComponent {
    fun getDeleteHabitUseCase(): DeleteHabitUseCase

    fun getLoadHabitsUseCase(): LoadHabitsUseCase

    fun getGetHabitByIdUseCase(): GetHabitByIdUseCase

    fun getInsertHabitUseCase(): InsertHabitUseCase

    fun getAccomplishHabitUseCase(): AccomplishHabitUseCase

    fun getSyncHabitsWithRemoteUseCase(): SyncHabitsWithRemoteUseCase

    fun getGlide(): RequestManager
}