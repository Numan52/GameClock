package com.example.gameclock.models

import androidx.compose.runtime.Composable
import com.example.gameclock.R

interface Puzzle {
    val id: Int
    val icon: Int
    val name: String
    val description: String
    val puzzleType: PuzzleType

    @Composable
    fun DisplayPuzzle(
        alarmId: String,
        onPuzzleSolved: () -> Unit,
        onEmergencyStop: () -> Unit,
        showEmergencyButton: Boolean
    )
}

fun getPuzzleOptions() : List<Puzzle> {
    return listOf(
        MemoryPuzzle(
            id = 1,
            icon= R.drawable.mental_capacity_intelligence_remember_intellect_svgrepo_com,
            name = "Memory Game",
            description = "Match pairs of cards to improve your memory.",
            puzzleType = PuzzleType.MEMORY_GAME
        ),
        MathPuzzle(
            id = 2,
            icon = R.drawable.maths_technology_svgrepo_com,
            name = "Math Problem",
            description = "Solve math problems to test your arithmetic skills.",
            puzzleType = PuzzleType.MATH_PROBLEM
        ),
        JigsawPuzzle(
            id = 3,
            icon = R.drawable.puzzle_jigsaw_svgrepo_com,
            name = "Jigsaw Puzzle",
            description = "Rearrange the pieces of an image to reconstruct the original image.",
            puzzleType = PuzzleType.PATTERN_RECOGNITION
        )
    )
}





