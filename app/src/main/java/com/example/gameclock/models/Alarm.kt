package com.example.gameclock.models

import java.time.LocalDate

data class Alarm(
    val id: String,
    val hour: Int,
    val minute: Int,
    val date: LocalDate?,
    val recurringDays: Set<String> = emptySet(),
    val ringtone: String,
    val puzzle: String
)
