package com.example.gameclock.navigation

const val DETAIL_ARGUMENT_KEY = "alarmId"
const val PUZZLE_ID = "puzzleId"
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

    object PuzzleSelectionScreen : Screen("puzzleSelection/{$DETAIL_ARGUMENT_KEY}") {
        fun withId(id: String): String {
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id)
        }
    }

    object PuzzlePreviewScreen : Screen("puzzlePreview/{$PUZZLE_ID}") {
        fun withId(id: String): String {
            return this.route.replace(oldValue = "{$PUZZLE_ID}", newValue = id)
        }
    }

    object PuzzleScreen : Screen("puzzle")
}
