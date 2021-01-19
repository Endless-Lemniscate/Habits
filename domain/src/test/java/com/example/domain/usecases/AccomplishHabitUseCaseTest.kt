package com.example.domain.usecases

import com.example.domain.model.Habit
import com.example.domain.model.Result
import com.example.domain.model.enums.*
import com.example.domain.repository.LocalHabitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import java.util.*


@ExperimentalCoroutinesApi
class AccomplishHabitUseCaseTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var repository: LocalHabitRepository
    private lateinit var useCase: AccomplishHabitUseCase
    private lateinit var habit: Habit

    @Before
    fun setup() {
        repository = Mockito.mock(LocalHabitRepository::class.java)
        useCase = AccomplishHabitUseCase(repository)
        habit = Habit("", "", Date(), 5, HabitPeriod.HOUR, HabitType.BAD,
            HabitPriority.HIGH, arrayListOf(), 1, EntityStatus.OK)
        habit.id = 25
    }

    @Test
    fun `AccomplishHabitUseCase executes insertHabitDone`() = runBlockingTest {

        val habitId = 25
        val doneDates = arrayListOf(Date(), Date(), Date())
        habit.id = habitId
        habit.doneDates = doneDates

        useCase.accomplishHabit(habit)

        fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

        Mockito.verify(repository).insertDoneDate(any(Int::class.java), any(Date::class.java))
    }

    @ExperimentalStdlibApi
    @Test
    fun `AccomplishHabitUseCase on good habit and count is less than needed returns String`() = runBlockingTest {

        val actualDoneTimes = 3
        //prepare doneDates array
        for (i in 1..actualDoneTimes) {
            habit.doneDates.add(Date())
        }

        habit.apply {
            id = 25
            count = 10
            type = HabitType.GOOD
        }

        val result = useCase.accomplishHabit(habit)
        if (result is Result.Success) {
            val string = result.data
            val expectedString = "стоит выполнить еще ${habit.count - actualDoneTimes - 1} раз"
            assertEquals(expectedString, string)
        }

    }

    @ExperimentalStdlibApi
    @Test
    fun `AccomplishHabitUseCase on good habit and count is more than needed returns String`() = runBlockingTest {

        val actualDoneTimes = 20
        //prepare doneDates array
        for (i in 1..actualDoneTimes) {
            habit.doneDates.add(Date())
        }

        habit.apply {
            count = 3
            type = HabitType.GOOD
        }

        val result = useCase.accomplishHabit(habit)
        if (result is Result.Success) {
            val string = result.data
            val expectedString = "You are breathtaking"
            assertEquals(expectedString, string)
        }
    }

    @ExperimentalStdlibApi
    @Test
    fun `AccomplishHabitUseCase on bad habit and count is less than needed returns String`() = runBlockingTest {

        val actualDoneTimes = 3
        //prepare doneDates array
        for (i in 1..actualDoneTimes) {
            habit.doneDates.add(Date())
        }
        println(habit.doneDates)

        habit.apply {
            count = 10
            type = HabitType.BAD
        }

        val result = useCase.accomplishHabit(habit)
        if (result is Result.Success) {
            val string = result.data
            val expectedString = "можете выполнить еще ${habit.count - actualDoneTimes - 1} раза"
            assertEquals(expectedString, string)
        }
    }

    @ExperimentalStdlibApi
    @Test
    fun `AccomplishHabitUseCase on bad habit and count is more than needed returns String`() = runBlockingTest {

        val actualDoneTimes = 15
        //prepare doneDates array
        for (i in 1..actualDoneTimes) {
            habit.doneDates.add(Date())
        }

        habit.apply {
            count = 10
            type = HabitType.BAD
        }

        val result = useCase.accomplishHabit(habit)
        if (result is Result.Success) {
            val string = result.data
            val expectedString = "Хватит это делать"
            assertEquals(expectedString, string)
        }
    }


}