package com.example.domain.usecases

import com.example.domain.model.*
import com.example.domain.model.enums.*
import com.example.domain.repository.LocalHabitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.util.*


@ExperimentalCoroutinesApi
class DeleteHabitUseCaseTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Test
    fun `DeleteHabitUseCase executes deleteHabit`() = runBlockingTest {
        val habitRepository = Mockito.mock(LocalHabitRepository::class.java)
        val useCase = DeleteHabitUseCase(habitRepository)

        val habit = Habit("", "", Date(), 5, HabitPeriod.HOUR,
            HabitType.BAD, HabitPriority.HIGH, arrayListOf(), 1, EntityStatus.OK)

        useCase.deleteHabit(habit)
        Mockito.verify(habitRepository).deleteHabit(habit)

    }
}