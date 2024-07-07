package com.example.gameclock.helper

import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonSerializationContext
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Converts LocalDate to JSON string
class LocalDateSerializer : JsonSerializer<LocalDate> {
    override fun serialize(
        src: LocalDate,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement { // Convert LocalDate to a JSON string in ISO_LOCAL_DATE format
        return JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE))
    }
}

// Converts JSON string to LocalDate
class LocalDateDeserializer : JsonDeserializer<LocalDate> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): LocalDate { // Convert JSON string in ISO_LOCAL_DATE format to LocalDate
        return LocalDate.parse(json.asString, DateTimeFormatter.ISO_LOCAL_DATE)
    }
}