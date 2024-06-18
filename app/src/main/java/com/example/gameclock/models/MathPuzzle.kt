package com.example.gameclock.models

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.gameclock.R

class MathPuzzle(
    override val id: Int = 2,
    override val icon: Int = R.drawable.maths_technology_svgrepo_com,
    override val name: String = "Math Problem",
    override val description: String = "Solve math problems to test your arithmetic skills.",
    override val puzzleType: PuzzleType = PuzzleType.MATH_PROBLEM
) : Puzzle {


    @Composable
    override fun DisplayPuzzle(
        alarmId: String,
        onPuzzleSolved: () -> Unit,
        onEmergencyStop: () -> Unit
    ) {
        TODO("Not yet implemented")
    }
}