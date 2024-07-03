package com.example.gameclock.models

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gameclock.R
import kotlin.random.Random

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
        val viewModel: MathPuzzleViewModel = viewModel()
        val (num1, num2) = remember { viewModel.generateRandomNumbers() } // Remember the generated numbers
        val correctAnswer = num1 + num2

        val (userAnswer, setUserAnswer) = remember { mutableStateOf("") }
        val (feedback, setFeedback) = remember { mutableStateOf("") }

        Column {
            Text(text = "Solve the following problem:")
            Text(text = "$num1 + $num2 = ?")

            Spacer(modifier = Modifier.height(16.dp))

            // Input field for user to enter the answer
            TextField(
                value = userAnswer,
                onValueChange = setUserAnswer,
                label = { Text("Your Answer") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (userAnswer.toIntOrNull() == correctAnswer) {
                    setFeedback("Correct!")
                    onPuzzleSolved()
                } else {
                    setFeedback("Try again!")
                }
            }) {
                Text(text = "Submit")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = feedback)

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onEmergencyStop) {
                Text(text = "Emergency Stop")
            }
        }
    }
}

class MathPuzzleViewModel : ViewModel() {
    fun generateRandomNumbers(): Pair<Int, Int> {
        return Random.nextInt(1, 100) to Random.nextInt(1, 100)
    }
}


