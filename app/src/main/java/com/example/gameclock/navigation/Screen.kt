package com.example.gameclock.navigation

// Constants for argument keys
const val DETAIL_ARGUMENT_KEY = "alarmId"
const val PUZZLE_ID = "puzzleId"

// Sealed class to define the screens and their routes
sealed class Screen(val route: String) {
    // Home screen
    object HomeScreen : Screen("home")

    // Alarm details screen with an argument
    object AlarmDetailsScreen : Screen("alarmDetails/{$DETAIL_ARGUMENT_KEY}") {
        fun withId(id: String): String { // create a route with the given ID
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id)
        }
    }

    // Ringtone selection screen with an argument
    object RingtoneScreen : Screen("ringtoneSelection/{$DETAIL_ARGUMENT_KEY}") {
        fun withId(id: String): String {
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id)
        }
    }

    // Puzzle selection screen with an argument
    object PuzzleSelectionScreen : Screen("puzzleSelection/{$DETAIL_ARGUMENT_KEY}") {
        fun withId(id: String): String {
            return this.route.replace(oldValue = "{$DETAIL_ARGUMENT_KEY}", newValue = id)
        }
    }

    // Puzzle preview screen with an argument
    object PuzzlePreviewScreen : Screen("puzzlePreview/{$PUZZLE_ID}") {
        fun withId(id: String): String {
            return this.route.replace(oldValue = "{$PUZZLE_ID}", newValue = id)
        }
    }

    // Generic puzzle screen without arguments
    object PuzzleScreen : Screen("puzzle")
}
