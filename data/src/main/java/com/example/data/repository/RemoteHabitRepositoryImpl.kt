package com.example.data.repository

import android.util.Log
import com.example.data.network.DoubletappApiService
import com.example.domain.model.Habit
import com.example.domain.model.Result
import com.example.domain.repository.RemoteHabitRepository
import com.google.gson.JsonObject
import java.util.*


class RemoteHabitRepositoryImpl(private val api: DoubletappApiService): RemoteHabitRepository {

    override suspend fun getHabits(): Result<List<Habit>> {
        return try {
            Result.Success(api.getHabits())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun insertHabit(habit: Habit): Result<String> {
        return try {
            val uid = api.insertHabit(habit).get("uid").asString
            Result.Success(uid)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteHabit(uid: String): Result<Unit> {
        val idJson = JsonObject()
        idJson.addProperty("uid", uid)

        return try {
            api.deleteHabit(idJson)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun habitDone(uid: String, date: Date): Result<Unit> {
        val jsonHabitDone = JsonObject()
        jsonHabitDone.addProperty("date", date.time)
        jsonHabitDone.addProperty("habit_uid", uid)

        return try {
            api.habitDone(jsonHabitDone)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }

    }

}