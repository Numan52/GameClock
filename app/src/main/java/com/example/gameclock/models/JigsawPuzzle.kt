package com.example.gameclock.models

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameclock.R

class JigsawPuzzle(
    override val id: Int = 3,
    override val icon: Int = R.drawable.puzzle_jigsaw_svgrepo_com,
    override val name: String = "Jigsaw Puzzle",
    override val description: String = "Rearrange the pieces of an image to reconstruct the original image.",
    override val puzzleType: PuzzleType = PuzzleType.PATTERN_RECOGNITION
) : Puzzle {

    @Composable
    override fun DisplayPuzzle(
        alarmId: String,
        onPuzzleSolved: () -> Unit,
        onEmergencyStop: () -> Unit,
        showEmergencyButton: Boolean
    ) {
        val imagePieces = generateImagePieces()
        var shuffledPieces by remember { mutableStateOf(shufflePieces(imagePieces)) }
        var selectedPiece by remember { mutableStateOf<Int?>(null) }
        var attempts by remember { mutableStateOf(0) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Jigsaw Puzzle", fontSize = 24.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))

            JigsawBoard(
                pieces = shuffledPieces,
                onPieceClick = { index ->
                    if (selectedPiece == null) {
                        selectedPiece = index
                    } else {
                        shuffledPieces = shuffledPieces.swap(selectedPiece!!, index)
                        selectedPiece = null
                        attempts++
                        if (shuffledPieces == imagePieces) {
                            onPuzzleSolved()
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (showEmergencyButton) {
                Button(onClick = onEmergencyStop) {
                    Text(text = "Emergency Stop")
                }
            }
        }
    }

    private fun List<Painter>.swap(index1: Int, index2: Int): List<Painter> {
        return toMutableList().apply {
            val temp = this[index1]
            this[index1] = this[index2]
            this[index2] = temp
        }
    }

    @Composable
    private fun JigsawBoard(
        pieces: List<Painter>,
        onPieceClick: (Int) -> Unit
    ) {
        Column {
            for (i in 0..1) {
                Row {
                    for (j in 0..1) {
                        val index = i * 2 + j
                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(150.dp)
                                .background(Color.Gray, RoundedCornerShape(8.dp))
                                .clickable { onPieceClick(index) },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(painter = pieces[index], contentDescription = null)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun generateImagePieces(): List<Painter> {
        return listOf(
            painterResource(id = R.drawable.image_piece_1),
            painterResource(id = R.drawable.image_piece_2),
            painterResource(id = R.drawable.image_piece_3),
            painterResource(id = R.drawable.image_piece_4)
        )
    }

    private fun shufflePieces(pieces: List<Painter>): List<Painter> {
        var shuffled: List<Painter>
        do {
            shuffled = pieces.shuffled()
        } while (shuffled == pieces)
        return shuffled
    }
}
