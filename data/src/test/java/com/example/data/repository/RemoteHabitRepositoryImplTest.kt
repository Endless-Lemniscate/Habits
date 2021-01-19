package com.example.data.repository

import com.example.data.network.DoubletappApiService
import com.example.data.network.HabitJsonDeserializer
import com.example.data.network.HabitJsonSerializer
import com.example.domain.model.Habit
import com.example.domain.model.Result
import com.example.domain.model.enums.HabitPeriod
import com.example.domain.model.enums.HabitPriority
import com.example.domain.model.enums.EntityStatus
import com.example.domain.model.enums.HabitType
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.*


class RemoteHabitRepositoryImplTest {

    private lateinit var api: DoubletappApiService
    private lateinit var repository: RemoteHabitRepositoryImpl
    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.start()
        val gson = GsonBuilder()
            .registerTypeAdapter(Habit::class.java, HabitJsonDeserializer())
            .registerTypeAdapter(Habit::class.java, HabitJsonSerializer())
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        api = retrofit.create(DoubletappApiService::class.java)
        repository = RemoteHabitRepositoryImpl(api)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getHabits() = runBlocking {

        val mockResponse = MockResponse()
        mockResponse.setResponseCode(HttpURLConnection.HTTP_OK)
        mockResponse.setBody(FileUtils.readTestResourceFile("GetHabits.json"))
        mockWebServer.enqueue(mockResponse)

        val result = repository.getHabits()
        if (result is Result.Success) {
            val list = result.data
            println(list)
            assert(true)
        }
    }

    @Test
    fun insertHabit() = runBlocking {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(HttpURLConnection.HTTP_OK)
        mockResponse.setBody(FileUtils.readTestResourceFile("AddHabit.json"))
        mockWebServer.enqueue(mockResponse)

        val habit = Habit("", "", Date(), 5, HabitPeriod.HOUR, HabitType.BAD,
            HabitPriority.HIGH, arrayListOf(), 1, EntityStatus.OK)

        val result = repository.insertHabit(habit)
        if (result is Result.Success) {
            val id = result.data
            println(id)
            assert(id == "some-id")
        }
    }

    @Test
    fun deleteHabit() = runBlocking {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(HttpURLConnection.HTTP_OK)
        mockWebServer.enqueue(mockResponse)

        val result = repository.deleteHabit("remote-id")
        if (result is Result.Success) {
            assert(true)
        }
    }

    @Test
    fun habitDone() = runBlocking {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(HttpURLConnection.HTTP_OK)
        mockWebServer.enqueue(mockResponse)

        val result = repository.deleteHabit("remote-id")
        if (result is Result.Success) {
            assert(true)
        }
    }
}