package com.example.gameclock.Repository

import android.content.Context
import com.example.gameclock.models.Alarm
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AlarmRepository(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("alarms", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveAlarms(alarms: List<Alarm>) {
        val json = gson.toJson(alarms)
        sharedPreferences.edit().putString("alarms", json).apply()
    }

    fun loadAlarms(): List<Alarm> {
        val json = sharedPreferences.getString("alarms", null)
        return if (json != null) {
            val type = object : TypeToken<List<Alarm>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }
}