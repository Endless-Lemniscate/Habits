package com.example.domain.usecases

import com.example.domain.repository.LocalHabitRepository
import org.junit.Test
import org.mockito.Mockito.*


class LoadHabitsUseCaseTest {

    @Test
    fun `LoadHabitUseCase loads all habits`() {
        val habitRepository = mock(LocalHabitRepository::class.java)

        val useCase = LoadHabitsUseCase(habitRepository)

        val name = "name"
        val sort = 1

        useCase.loadHabits(name, sort)
        verify(habitRepository).loadHabits("%$name%", sort)

    }
}