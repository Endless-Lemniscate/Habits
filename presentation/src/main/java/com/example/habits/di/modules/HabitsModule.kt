package com.example.habits.di.modules

import android.content.Context
import androidx.room.Room
import com.example.data.db.HabitDao
import com.example.data.db.AppDatabase
import com.example.data.repository.HabitRepositoryImpl
import com.example.domain.repository.HabitRepository
import com.example.domain.usecases.*
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton


@Module
class HabitsModule {
    @Provides
    fun provideAccomplishHabitUseCase(habitsRepository: HabitRepository): AccomplishHabitUseCase {
        return AccomplishHabitUseCase(habitsRepository, Dispatchers.IO)
    }

    @Provides
    fun provideDeleteHabitUseCase(habitsRepository: HabitRepository): DeleteHabitUseCase {
        return DeleteHabitUseCase(habitsRepository, Dispatchers.IO)
    }

    @Provides
    fun provideGetHabitByIdUseCase(habitsRepository: HabitRepository): GetHabitByIdUseCase {
        return GetHabitByIdUseCase(habitsRepository, Dispatchers.IO)
    }

    @Provides
    fun provideInsertHabitUseCase(habitsRepository: HabitRepository): InsertHabitUseCase {
        return InsertHabitUseCase(habitsRepository, Dispatchers.IO)
    }

    @Provides
    fun provideLoadHabitsUseCase(habitsRepository: HabitRepository): LoadHabitsUseCase {
        return LoadHabitsUseCase(habitsRepository, Dispatchers.IO)
    }

    @Provides
    fun provideHabitsRepository(habitDao: HabitDao): HabitRepository {
        return HabitRepositoryImpl(habitDao)
    }

    @Provides
    fun provideHabitDao(db: AppDatabase): HabitDao = db.habitDao()

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
    }
}