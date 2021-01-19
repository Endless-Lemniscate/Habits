package com.example.domain.usecases

import com.example.domain.repository.LocalHabitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito


@ExperimentalCoroutinesApi
class GetHabitByIdUseCaseTest {

    @Test
    fun `GetHabitByIdUseCase executes getHabitById`() = runBlockingTest {
        val habitRepository = Mockito.mock(LocalHabitRepository::class.java)

        val useCase = GetHabitByIdUseCase(habitRepository)
        val id = 10
        useCase.getHabitById(id)
        Mockito.verify(habitRepository).getHabitById(id)

    }
}