package com.example.gameclock.models

import com.example.gameclock.R

data class Puzzle(
    val id: Int,
    val icon: Int,
    val name: String,
    val description: String,
    val puzzleType: PuzzleType
)

fun getPuzzleOptions() : List<Puzzle> {
    return listOf(
        Puzzle(
            id = 1,
            icon= R.drawable.mental_capacity_intelligence_remember_intellect_svgrepo_com,
            name = "Memory Game",
            description = "Match pairs of cards to improve your memory.",
            puzzleType = PuzzleType.MEMORY_GAME
        ),
        Puzzle(
            id = 2,
            icon = R.drawable.maths_technology_svgrepo_com,
            name = "Math Problem",
            description = "Solve math problems to test your arithmetic skills.",
            puzzleType = PuzzleType.MATH_PROBLEM
        ),
        Puzzle(
            id = 3,
            icon = R.drawable.puzzle_jigsaw_svgrepo_com,
            name = "Jigsaw Puzzle",
            description = "Rearrange the pieces of an image to reconstruct the original image.",
            puzzleType = PuzzleType.PATTERN_RECOGNITION
        )
    )
}




