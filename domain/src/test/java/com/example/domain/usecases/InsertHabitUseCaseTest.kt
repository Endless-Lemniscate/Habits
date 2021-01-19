package com.example.domain.usecases

import com.example.domain.model.enums.HabitPeriod
import com.example.domain.model.enums.HabitPriority
import com.example.domain.model.enums.HabitType
import com.example.domain.model.Habit
import com.example.domain.model.enums.EntityStatus
import com.example.domain.repository.LocalHabitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.util.*


@ExperimentalCoroutinesApi
class InsertHabitUseCaseTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Test
    fun `InsertHabitUseCase executes all habits`() = runBlockingTest {
            val habitRepository = Mockito.mock(LocalHabitRepository::class.java)
            val useCase = InsertHabitUseCase(habitRepository)

            val habit = Habit("", "", Date(), 5, HabitPeriod.HOUR,
                HabitType.BAD, HabitPriority.HIGH, arrayListOf(), 1, EntityStatus.OK)
            habit.id = 25

            useCase.insertHabit(habit)
            Mockito.verify(habitRepository).insertHabit(habit)

    }
}