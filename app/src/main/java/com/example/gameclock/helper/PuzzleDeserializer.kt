package com.example.gameclock.helper

import android.util.Log
import com.example.gameclock.models.JigsawPuzzle
import com.example.gameclock.models.MathPuzzle
import com.example.gameclock.models.MemoryPuzzle
import com.example.gameclock.models.Puzzle
import com.example.gameclock.models.PuzzleType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class PuzzleDeserializer : JsonDeserializer<Puzzle>, JsonSerializer<Puzzle> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Puzzle {
        val jsonObject = json.asJsonObject
        Log.i("puzzle_deserializer", jsonObject.toString())
        // Ensure 'puzzle' element is not null before accessing it

        // Get the puzzle type from the JSON object
        val puzzleType = PuzzleType.valueOf(jsonObject.get("type").asString)

        // Deserialize based on the puzzle type
        return when (puzzleType) {
            PuzzleType.MEMORY_GAME -> context.deserialize<MemoryPuzzle>(jsonObject, MemoryPuzzle::class.java)
            PuzzleType.MATH_PROBLEM -> context.deserialize<MathPuzzle>(jsonObject, MathPuzzle::class.java)
            PuzzleType.PATTERN_RECOGNITION -> context.deserialize<JigsawPuzzle>(jsonObject, JigsawPuzzle::class.java)
        }
    }

    override fun serialize(src: Puzzle?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        val jsonObject = JsonObject()
        Log.i("jsonObject", jsonObject.toString())
        jsonObject.addProperty("id", src?.id.toString())
        jsonObject.addProperty("name", src?.name.toString())
        jsonObject.addProperty("description", src?.description.toString())
        jsonObject.addProperty("icon", src?.icon.toString())
        jsonObject.addProperty("type", src?.puzzleType.toString())

        Log.i("jsonObject", jsonObject.toString())
        // Add specific fields for each Puzzle subtype if necessary
        return jsonObject
    }
}