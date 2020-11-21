package com.example.data.network

import android.util.Log
import com.example.domain.model.Habit
import com.google.gson.*
import java.lang.reflect.Type


class HabitJsonSerializer : JsonSerializer<Habit> {
    override fun serialize(src: Habit?, typeOfSrc: Type?, context: JsonSerializationContext?
    ): JsonElement {

        val json = JsonObject().apply {
            addProperty("title", src?.name)
            addProperty("description", src?.description)
            addProperty("date", src?.date?.time)
            addProperty("count", src?.count)
            addProperty("frequency", src?.period?.ordinal)
            addProperty("type", src?.type?.ordinal)
            addProperty("priority", src?.priority?.ordinal)
            addProperty("color", src?.color)
        }

        //if remote id presented then add to json
        src?.remoteId?.let {
            json.addProperty("uid", it)
        }

        return json
    }
}