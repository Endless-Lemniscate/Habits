package com.example.data.network

import com.example.domain.model.Habit
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


const val API_KEY = "3dd01ab5-850d-44d6-b608-ed20bca01f86"
const val BASE_URL = "https://droid-test-server.doubletapp.ru/api/"

interface DoubletappApiService {

    @Headers(
        "accept: application/json",
        "Authorization: $API_KEY"
    )
    @GET("habit")
    suspend fun getHabits(): List<Habit>


    @Headers(
        "accept: application/json",
        "Authorization: $API_KEY",
        "Content-Type: application/json"
    )
    @PUT("habit")
    suspend fun insertHabit(@Body habit: Habit) : JsonObject

    @Headers(
        "accept: application/json",
        "Authorization: $API_KEY",
        "Content-Type: application/json"
    )
    @HTTP(method = "DELETE", path = "habit", hasBody = true)
    suspend fun deleteHabit(@Body json: JsonObject)


    @Headers(
        "accept: application/json",
        "Authorization: $API_KEY",
        "Content-Type: application/json"
    )
    @POST("habit_done")
    suspend fun habitDone(@Body json: JsonObject): JsonObject


    companion object {
        private val gson = GsonBuilder()
            .registerTypeAdapter(Habit::class.java, HabitJsonDeserializer())
            .registerTypeAdapter(Habit::class.java, HabitJsonSerializer())
            .create()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        fun get(): DoubletappApiService {
            return retrofit.create(DoubletappApiService::class.java)
        }

    }
}



