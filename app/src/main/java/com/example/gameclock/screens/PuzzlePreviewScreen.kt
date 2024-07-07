package com.example.gameclock.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.gameclock.models.Puzzle
import com.example.gameclock.models.getPuzzleOptions

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuzzlePreviewScreen(
    navController: NavController,
    puzzleId: String,
    context: Context
) {
    val puzzle: Puzzle? = getPuzzleOptions().find { puzzle -> puzzle.id == puzzleId.toInt() }
    
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Preview") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Back Button")
                    }
                }
            )
        })
    { innerPadding ->
        puzzle?.DisplayPuzzle(
            alarmId = "Preview",
            onPuzzleSolved = { navController.popBackStack() },
            onEmergencyStop = { navController.popBackStack() },
            showEmergencyButton = false
        )
    }
}