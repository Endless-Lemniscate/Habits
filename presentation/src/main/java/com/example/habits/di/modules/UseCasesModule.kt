package com.example.habits.di.modules

import com.example.domain.repository.LocalHabitRepository
import com.example.domain.repository.RemoteHabitRepository
import com.example.domain.usecases.*
import dagger.Module
import dagger.Provides


@Module
class UseCasesModule {

    @Provides
    fun provideSyncHabitsWithRemoteUseCase(local: LocalHabitRepository, remote: RemoteHabitRepository): SyncHabitsWithRemoteUseCase {
        return SyncHabitsWithRemoteUseCase(local, remote)
    }

    @Provides
    fun provideAccomplishHabitUseCase(habitsRepositoryLocal: LocalHabitRepository): AccomplishHabitUseCase {
        return AccomplishHabitUseCase(habitsRepositoryLocal)
    }

    @Provides
    fun provideDeleteHabitUseCase(habitsRepositoryLocal: LocalHabitRepository): DeleteHabitUseCase {
        return DeleteHabitUseCase(habitsRepositoryLocal)
    }

    @Provides
    fun provideGetHabitByIdUseCase(habitsRepositoryLocal: LocalHabitRepository): GetHabitByIdUseCase {
        return GetHabitByIdUseCase(habitsRepositoryLocal)
    }

    @Provides
    fun provideInsertHabitUseCase(habitsRepositoryLocal: LocalHabitRepository): InsertHabitUseCase {
        return InsertHabitUseCase(habitsRepositoryLocal)
    }

    @Provides
    fun provideLoadHabitsUseCase(habitsRepositoryLocal: LocalHabitRepository): LoadHabitsUseCase {
        return LoadHabitsUseCase(habitsRepositoryLocal)
    }

}