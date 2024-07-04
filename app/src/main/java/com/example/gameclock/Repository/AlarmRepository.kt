package com.example.gameclock.Repository

import android.content.Context
import android.util.Log
import com.example.gameclock.helper.LocalDateDeserializer
import com.example.gameclock.helper.LocalDateSerializer
import com.example.gameclock.helper.PuzzleDeserializer
import com.example.gameclock.models.Alarm
import com.example.gameclock.models.Puzzle
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.time.LocalDate

class AlarmRepository(context: Context) {


    private val sharedPreferences = context.getSharedPreferences("alarms", Context.MODE_PRIVATE)
    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(Puzzle::class.java, PuzzleDeserializer())
        .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
        .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
        .create()

    fun saveAlarms(alarms: List<Alarm>) {
        Log.i("saveAlarm", alarms.toString())
        val json = gson.toJson(alarms)
        Log.i("AlarmRepository", "Saved JSON: $json") // Log the JSON being saved
        sharedPreferences.edit().putString("alarms", json).apply()
    }

    fun loadAlarms(): List<Alarm> {
        val json = sharedPreferences.getString("alarms", null)
        Log.i("AlarmRepository", "Loaded JSON: $json") // Log the JSON being loaded
        return if (json != null) {
            val type = object : TypeToken<List<Alarm>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }
}