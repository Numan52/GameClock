package com.example.gameclock.navigation

const val DETAIL_ARGUMENT_KEY = "alarmId"
sealed class Screen(val route: String) {
    object HomeScreen : Screen("home")
    object AlarmDetailsScreen : Screen("alarmDetails/{$DETAIL_ARGUMENT_KEY}") {
        fun withId(id: String): String {
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id)
        }
    }
    object RingtoneScreen : Screen("ringtoneSelection/{$DETAIL_ARGUMENT_KEY}") {
        fun withId(id: String): String {
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id)
        }
    }
    object PuzzleScreen : Screen("puzzle")
}