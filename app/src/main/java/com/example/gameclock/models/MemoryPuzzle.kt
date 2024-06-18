package com.example.gameclock.models

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.gameclock.R
import com.example.gameclock.models.Puzzle
import com.example.gameclock.models.PuzzleType

class MemoryPuzzle(
    override val id: Int = 1,
    override val icon: Int = R.drawable.mental_capacity_intelligence_remember_intellect_svgrepo_com,
    override val name: String = "Memory Game",
    override val description: String = "Match pairs of cards to improve your memory.",
    override val puzzleType: PuzzleType = PuzzleType.MEMORY_GAME
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