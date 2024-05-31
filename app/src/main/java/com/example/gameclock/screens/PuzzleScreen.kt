package com.example.gameclock.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gameclock.R
import com.example.gameclock.ViewModels.AlarmViewModel
import com.example.gameclock.puzzle.PuzzlePiece

@Composable
fun PuzzleScreen(
    alarmId: String,
    onPuzzleSolved: () -> Unit,
    onEmergencyStop: () -> Unit
) {
    var piecesPlaced by remember { mutableStateOf(0) }
    val totalPieces = 4 // For a simple 2x2 puzzle
    val alarmViewModel: AlarmViewModel = viewModel()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Solve the puzzle to stop the alarm", color = Color.White, modifier = Modifier.padding(16.dp))

            Box(
                modifier = Modifier
                    .size(300.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Row {
                        PuzzlePiece(R.drawable.image_piece_2, onPiecePlaced = { piecesPlaced++ })
                        PuzzlePiece(R.drawable.image_piece_4, onPiecePlaced = { piecesPlaced++ })
                    }
                    Row {
                        PuzzlePiece(R.drawable.image_piece_1, onPiecePlaced = { piecesPlaced++ })
                        PuzzlePiece(R.drawable.image_piece_3, onPiecePlaced = { piecesPlaced++ })
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Emergency stop button
            Button(onClick = {
                alarmViewModel.removeAlarmById(alarmId)
                onEmergencyStop()
            }) {
                Text(text = "Emergency Stop")
            }
        }
    }

    // Check if the puzzle is solved
    LaunchedEffect(piecesPlaced) {
        if (piecesPlaced == totalPieces) {
            alarmViewModel.removeAlarmById(alarmId)
            onPuzzleSolved()
        }
    }
}