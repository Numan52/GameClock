package com.example.gameclock.models

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gameclock.R
import com.example.gameclock.ViewModels.AlarmViewModel


class JigsawPuzzle(
    override val id: Int = 3,
    override val icon: Int = R.drawable.puzzle_jigsaw_svgrepo_com,
    override val name: String = "Jigsaw Puzzle",
    override val description: String = "Rearrange the pieces of an image to reconstruct the original image.",
    override val puzzleType: PuzzleType = PuzzleType.PATTERN_RECOGNITION
) : Puzzle {
    @Composable
    override fun DisplayPuzzle(alarmId: String,
                               onPuzzleSolved: () -> Unit,
                               onEmergencyStop: () -> Unit) {

            var piecesPlaced by remember { mutableStateOf(0) }
            val totalPieces = 4 // For a simple 2x2 puzzle


            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    androidx.compose.material3.Text(text = "Solve the puzzle to stop the alarm", color = Color.White, modifier = Modifier.padding(16.dp))

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
                        onEmergencyStop()
                    }) {
                        androidx.compose.material3.Text(text = "Emergency Stop")
                    }
                }
            }

            // Check if the puzzle is solved
            LaunchedEffect(piecesPlaced) {
                if (piecesPlaced == totalPieces) {
                    onPuzzleSolved()
                }
            }
        }

    @Composable
    fun PuzzlePiece(
        imageResId: Int,
        modifier: Modifier = Modifier,
        onPiecePlaced: () -> Unit
    ) {
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        Box(
            modifier = modifier
                .size(150.dp)
                .graphicsLayer(
                    translationX = offsetX,
                    translationY = offsetY
                )
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
                .background(Color.Gray, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}
