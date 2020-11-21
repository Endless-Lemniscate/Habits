package com.example.data.network

import com.example.domain.model.Habit
import com.example.domain.model.enums.HabitPeriod
import com.example.domain.model.enums.HabitPriority
import com.example.domain.model.enums.HabitStatus
import com.example.domain.model.enums.HabitType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList


class HabitJsonDeserializer : JsonDeserializer<Habit> {

    override fun deserialize(
        json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Habit {


        val habit = Habit(
            name = json.asJsonObject.get("title").asString,
            description = json.asJsonObject.get("description").asString,
            date = Date(json.asJsonObject.get("date").asLong),
            count = json.asJsonObject.get("count").asInt,
            period = HabitPeriod.values()[json.asJsonObject.get("frequency").asInt],
            type = HabitType.values()[json.asJsonObject.get("type").asInt],
            priority = HabitPriority.values()[json.asJsonObject.get("priority").asInt],
            doneDates = ArrayList(json.asJsonObject.get("done_dates").asJsonArray.map { Date(it.asLong) }),
            color = json.asJsonObject.get("color").asInt,
            status = HabitStatus.OK
        )
        habit.remoteId = json.asJsonObject.get("uid").asString

        return habit
    }
}