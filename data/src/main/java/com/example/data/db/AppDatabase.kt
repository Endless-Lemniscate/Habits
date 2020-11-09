package com.example.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.models.*


@Database(entities = [RoomHabit::class, RoomHabitDone::class], version = 1)
@TypeConverters(AllTypeConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun habitDao(): HabitDao
}