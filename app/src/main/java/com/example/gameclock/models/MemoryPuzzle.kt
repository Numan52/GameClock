package com.example.gameclock.models

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gameclock.R
import kotlinx.coroutines.delay

class MemoryPuzzle(
    override val id: Int = 1,
    override val icon: Int = R.drawable.mental_capacity_intelligence_remember_intellect_svgrepo_com,
    override val name: String = "Memory Game",
    override val description: String = "Match pairs of cards to improve your memory.",
    override val puzzleType: PuzzleType = PuzzleType.MEMORY_GAME
) : Puzzle {

    private val cardImages = listOf(
        R.drawable.books_image,
        R.drawable.chicken_image,
        R.drawable.diamond_image,
        R.drawable.couch_image,
        R.drawable.shoes_image,
        R.drawable.hat_image,
    )

    private val cards = cardImages.flatMap { image ->
        listOf(
            Card(id = image.hashCode() * 2, image = image),
            Card(id = image.hashCode() * 2 + 1, image = image)
        )
    }.shuffled()

    @Composable
    override fun DisplayPuzzle(
        alarmId: String,
        onPuzzleSolved: () -> Unit,
        onEmergencyStop: () -> Unit,
        showEmergencyButton: Boolean
    ) {
        var gameState by remember { mutableStateOf(cards) }
        var selectedCards by remember { mutableStateOf<List<Card>>(emptyList()) }

        LaunchedEffect(selectedCards) {
            if (selectedCards.size == 2) {
                delay(500)
                if (selectedCards[0].image == selectedCards[1].image) {
                    gameState = gameState.map { card ->
                        if (card.image == selectedCards[0].image) {
                            card.copy(isRevealed = true, isMatched = true)
                        } else {
                            card
                        }
                    }
                    if (gameState.all { it.isMatched }) {
                        onPuzzleSolved()
                    }
                } else {
                    selectedCards[0].isRevealed = false
                    selectedCards[1].isRevealed = false
                }
                selectedCards = emptyList()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Memory Game", fontSize = 24.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))

            GameBoard(
                gameState = gameState,
                onCardClicked = { card ->
                    HandleCardClick(
                        card,
                        selectedCards,
                        gameState,
                        { selectedCard -> selectedCards += selectedCard }
                    )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (showEmergencyButton) {
                androidx.compose.material3.Button(onClick = {
                    onEmergencyStop()
                }) {
                    androidx.compose.material3.Text(text = "Emergency Stop")
                }
            }
        }
    }

    private fun HandleCardClick(
        card: Card,
        selectedCards: List<Card>,
        gameState: List<Card>,
        addSelectedCard: (Card) -> Unit
    ) {
        if (selectedCards.size < 2 && !card.isRevealed && !card.isMatched) {
            card.isRevealed = true
            addSelectedCard(card)
        }
    }

    @Composable
    private fun GameBoard(
        gameState: List<Card>,
        onCardClicked: (Card) -> Unit
    ) {
        val columns = 3
        Column {
            gameState.chunked(columns).forEach { row ->
                Row {
                    row.forEach { card ->
                        CardView(card = card, onClick = { onCardClicked(card) })
                    }
                }
            }
        }
    }

    @Composable
    private fun CardView(
        card: Card,
        onClick: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(110.dp)
                .background(Color.Gray, RoundedCornerShape(8.dp))
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            if (card.isRevealed || card.isMatched) {
                Image(painter = painterResource(card.image), contentDescription = null)
            } else {
                Box(modifier = Modifier.fillMaxSize().background(Color.Black))
            }
        }
    }
}

@Composable
fun PreviewMemoryPuzzle() {
    MemoryPuzzle().DisplayPuzzle(
        alarmId = "test_alarm",
        onPuzzleSolved = { Log.i("win", "you won") },
        onEmergencyStop = { /* Handle emergency stop */ },
        showEmergencyButton = true
    )
}
