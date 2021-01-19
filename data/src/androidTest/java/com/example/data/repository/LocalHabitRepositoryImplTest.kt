package com.example.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.data.db.AppDatabase
import com.example.data.db.HabitDao
import com.example.domain.model.*
import com.example.domain.model.enums.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import org.junit.Rule


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class LocalHabitRepositoryImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule ()

    private lateinit var database: AppDatabase
    private lateinit var dao: HabitDao
    private lateinit var repository: LocalHabitRepositoryImpl
    private lateinit var habit: Habit

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.habitDao()
        repository = LocalHabitRepositoryImpl(dao)

        habit = Habit("", "", Date(), 5, HabitPeriod.HOUR, HabitType.BAD,
            HabitPriority.HIGH, arrayListOf(), 1, EntityStatus.OK)
    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertHabit() = runBlockingTest {
        repository.insertHabit(habit)

        val list = repository.loadHabits().first()
        assertThat(list).contains(habit)
    }

    @Test
    fun deleteHabit() = runBlockingTest {
        repository.insertHabit(habit)
        val list = repository.loadHabits().first()
        repository.deleteHabit(list[0])

        val list2 = repository.loadHabits().first()
        assertThat(list2.size).isEqualTo(0)
    }

    @Test
    fun getHabitById() = runBlockingTest {
        repository.insertHabit(habit)

        val list = repository.loadHabits().first()
        val requestedId = list[0].id
        val responseId = repository.getHabitById(requestedId).id

        assertThat(requestedId).isEqualTo(responseId)
    }

    @Test
    fun insertDoneDate() = runBlockingTest {
        val date = Date()

        repository.insertHabit(habit)
        val id = repository.loadHabits().first()[0].id

        repository.insertDoneDate(id, date)
        val habit = repository.getHabitById(id)

        assertThat(habit.doneDates).contains(date)
    }

    @Test
    fun insertHabitDone() = runBlockingTest {
        val date = Date()

        repository.insertHabit(habit)
        val id = repository.loadHabits().first()[0].id

        repository.insertDoneDate(id, date)
        val habit = repository.getHabitById(id)

        assertThat(habit.doneDates).contains(date)
    }

}