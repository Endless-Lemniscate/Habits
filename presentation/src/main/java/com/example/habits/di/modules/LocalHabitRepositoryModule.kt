package com.example.habits.di.modules

import android.content.Context
import androidx.room.Room
import com.example.data.db.AppDatabase
import com.example.data.db.HabitDao
import com.example.data.repository.LocalHabitRepositoryImpl
import com.example.domain.repository.LocalHabitRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class LocalHabitRepositoryModule {
    @Provides
    fun provideHabitsRepository(habitDao: HabitDao): LocalHabitRepository {
        return LocalHabitRepositoryImpl(habitDao)
    }

    @Provides
    fun provideHabitDao(db: AppDatabase): HabitDao = db.habitDao()

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
    }
}