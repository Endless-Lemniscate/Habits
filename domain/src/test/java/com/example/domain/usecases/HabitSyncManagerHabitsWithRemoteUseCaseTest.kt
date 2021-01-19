package com.example.domain.usecases

import com.example.domain.model.Habit
import com.example.domain.model.Result
import com.example.domain.model.enums.HabitPeriod
import com.example.domain.model.enums.HabitPriority
import com.example.domain.model.enums.EntityStatus
import com.example.domain.model.enums.HabitType
import com.example.domain.repository.LocalHabitRepository
import com.example.domain.repository.RemoteHabitRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import java.util.*


@ExperimentalCoroutinesApi
class HabitSyncManagerHabitsWithRemoteUseCaseTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var local: LocalHabitRepository
    private lateinit var remote: RemoteHabitRepository
    private lateinit var useCase: SyncHabitsWithRemoteUseCase
    private lateinit var habit: Habit

    @Before
    fun setup() {
        local = Mockito.mock(LocalHabitRepository::class.java)
        remote = Mockito.mock(RemoteHabitRepository::class.java)
        useCase = SyncHabitsWithRemoteUseCase(local, remote)

        habit = Habit("", "", Date(), 5, HabitPeriod.HOUR, HabitType.BAD,
            HabitPriority.HIGH, arrayListOf(), 1, EntityStatus.OK)
        habit.id = 25
    }

    @Test
    fun `if status NOT_SYNCED executes remote_insertHabit`() = runBlockingTest {
        //set status deleted
        habit.status = EntityStatus.NOT_SYNCED

        //return flow of habits with status "DELETED"
        val flow = flowOf(listOf(habit, habit, habit))
        Mockito.`when`(local.getNotSyncedOrDeleted()).thenReturn(flow)

        //say what local storage is not empty to avoid getting habits from remote
        Mockito.`when`(local.isEmpty()).thenReturn(false)

        //return success with "remote_id" when remote.insertHabit function is requested
        Mockito.`when`(remote.insertHabit(habit)).thenReturn(Result.Success("remote_id"))

        //collect flow of UseCase to activate it
        val result = useCase.run()
        result.collect()

        //verify
        Mockito.verify(remote).insertHabit(habit)
    }

    @Test
    fun `if status NOT_SYNCED and doneDatesNs not empty executes remote_habitDone`() = runBlockingTest {

        val sizeDoneDates = 5
        val date = Date()

        //set status deleted
        habit.status = EntityStatus.NOT_SYNCED
        habit.doneDates = arrayListOf()
        habit.remoteId = "remote_id"

        for (i in 1..sizeDoneDates) {
            habit.doneDatesNs.add(date)
        }

        //return flow of habits with status "DELETED"
        val flow = flowOf(listOf(habit, habit, habit))
        Mockito.`when`(local.getNotSyncedOrDeleted()).thenReturn(flow)

        //say what local storage is not empty to avoid getting habits from remote
        Mockito.`when`(local.isEmpty()).thenReturn(false)

        //return success with "remote_id" when remote.insertHabit function is requested
        Mockito.`when`(remote.insertHabit(habit)).thenReturn(Result.Success("remote_id"))

        //collect flow of UseCase to activate it
        val result = useCase.run()
        result.collect()

        //verify insert was executed
        Mockito.verify(remote).insertHabit(habit)
        //verify remote.habitDone executed "doneDatesNs.size" times
        Mockito.verify(remote, times(sizeDoneDates)).habitDone(habit.remoteId!!, date)
    }

    @Test
    fun `if status DELETED executes remote_deleteHabit`() = runBlockingTest {
        //set status deleted
        habit.status = EntityStatus.DELETED
        habit.remoteId = "remote_id"

        //return flow of habits with status "DELETED"
        val flow = flowOf(listOf(habit, habit, habit))
        Mockito.`when`(local.getNotSyncedOrDeleted()).thenReturn(flow)

        //say what local storage is not empty to avoid getting habits from remote
        Mockito.`when`(local.isEmpty()).thenReturn(false)

        //return success when remote.deleteHabit function is requested
        Mockito.`when`(remote.deleteHabit(habit.remoteId!!)).thenReturn(Result.Success(Unit))

        //collect flow of UseCase to activate it
        val result = useCase.run()
        result.collect()

        //verify
        Mockito.verify(remote).deleteHabit(habit.remoteId!!)
    }

    @Test
    fun `if local db is empty executes remote_getHabits`() = runBlockingTest {

        //return empty flow
        val flow = flowOf<List<Habit>>()
        Mockito.`when`(local.getNotSyncedOrDeleted()).thenReturn(flow)

        //say what local storage is empty
        Mockito.`when`(local.isEmpty()).thenReturn(true)

        val remoteHabitsList = listOf(habit, habit, habit)
        //return success when remote.deleteHabit function is requested
        Mockito.`when`(remote.getHabits()).thenReturn(Result.Success(remoteHabitsList))

        //collect flow of UseCase to activate it
        val result = useCase.run()
        result.collect()

        //verify execute remote.getHabits
        Mockito.verify(remote).getHabits()
        //verify local.insertHabit executes 3 times
        Mockito.verify(local, times(3)).insertHabit(habit)
    }

}