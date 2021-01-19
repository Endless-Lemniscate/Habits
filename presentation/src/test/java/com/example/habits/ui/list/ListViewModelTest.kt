package com.example.habits.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.domain.model.enums.HabitPeriod
import com.example.domain.model.enums.HabitPriority
import com.example.domain.model.enums.HabitType
import com.example.domain.model.Habit
import com.example.domain.model.enums.EntityStatus
import com.example.domain.usecases.*
import com.example.habits.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import java.util.*


@ExperimentalCoroutinesApi
class ListViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val rule = InstantTaskExecutorRule ()

    private lateinit var viewModel: ListViewModel
    private lateinit var accomplishHabitUseCase: AccomplishHabitUseCase
    private lateinit var deleteHabitUseCase: DeleteHabitUseCase
    private lateinit var loadHabitsUseCase: LoadHabitsUseCase
    private lateinit var syncHabitsWithRemoteUseCase: SyncHabitsWithRemoteUseCase



    @Before
    fun setup() {
        accomplishHabitUseCase = mock(AccomplishHabitUseCase::class.java)
        deleteHabitUseCase = mock(DeleteHabitUseCase::class.java)
        loadHabitsUseCase = mock(LoadHabitsUseCase::class.java)
        syncHabitsWithRemoteUseCase = mock(SyncHabitsWithRemoteUseCase::class.java)

        val flow = flowOf(SyncStatus.InProgress(1))
        `when`(syncHabitsWithRemoteUseCase.run()).thenReturn(flow)

        viewModel = ListViewModel(loadHabitsUseCase, deleteHabitUseCase, accomplishHabitUseCase, syncHabitsWithRemoteUseCase)
    }

    private val habit = Habit("", "", Date(), 1, HabitPeriod.DAY,
        HabitType.BAD, HabitPriority.HIGH, arrayListOf(), 0, EntityStatus.NOT_SYNCED)


    @Test
    fun `deleteHabit executes deleteHabitUseCase`() =
        coroutineRule.testDispatcher.runBlockingTest {
            viewModel.deleteHabit(habit)

            verify(deleteHabitUseCase).syncedDeleteHabit(habit)
    }

    @Test
    fun `observe listHabits executes LoadHabitsUseCase`() =
        coroutineRule.testDispatcher.runBlockingTest {

            val flowHabits = flowOf(listOf(habit, habit))
            `when`(loadHabitsUseCase.loadHabits(anyString(), anyInt())).thenReturn(flowHabits)
            viewModel.listHabits.observeForever {}

            verify(loadHabitsUseCase, times(2)).loadHabits(anyString(), anyInt())
        }

    @Test
    fun `Set name filter executes LoadHabitsUseCase with name`() =
        coroutineRule.testDispatcher.runBlockingTest {

            val flowHabits = flowOf(listOf(habit, habit))
            `when`(loadHabitsUseCase.loadHabits(anyString(), anyInt())).thenReturn(flowHabits)
            viewModel.listHabits.observeForever {}

            viewModel.setNameFilter("name")

            verify(loadHabitsUseCase).loadHabits("name", 0)
        }

    @Test
    fun `Set sort executes LoadHabitsUseCase with sort`() =
        coroutineRule.testDispatcher.runBlockingTest {

            val flowHabits = flowOf(listOf(habit, habit))
            `when`(loadHabitsUseCase.loadHabits(anyString(), anyInt())).thenReturn(flowHabits)
            viewModel.listHabits.observeForever {}
            viewModel.setSort(1)

            verify(loadHabitsUseCase).loadHabits("", 1)
        }

    @Test
    fun `SaveScrollState saves Int to mutableScrollState`() =
        coroutineRule.testDispatcher.runBlockingTest {

            val flowHabits = flowOf(listOf(habit, habit))
            `when`(loadHabitsUseCase.loadHabits(anyString(), anyInt())).thenReturn(flowHabits)
            viewModel.listHabits.observeForever {}

            viewModel.saveScrollState(555)
            assertEquals(viewModel.scrollState.value, 555)
        }

    @Test
    fun `accomplishHabit executes accomplishHabitUseCase`() =
        coroutineRule.testDispatcher.runBlockingTest {

            val flowHabits = flowOf(listOf(habit, habit))
            `when`(loadHabitsUseCase.loadHabits(anyString(), anyInt())).thenReturn(flowHabits)
            viewModel.listHabits.observeForever {}

            viewModel.accomplishHabit(habit)
            verify(accomplishHabitUseCase).accomplishHabit(habit)
        }

    @Test
    fun `viewModel init executes syncHabitsWithRemoteUseCase`() =
        coroutineRule.testDispatcher.runBlockingTest {
            verify(syncHabitsWithRemoteUseCase).run()
        }

}