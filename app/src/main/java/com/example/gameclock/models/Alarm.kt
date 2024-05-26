package com.example.gameclock.models

import java.time.LocalDate

data class Alarm(
    val id: String,
    val hour: Int,
    val minute: Int,
    val date: LocalDate
) {

}