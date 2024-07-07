package com.example.gameclock.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.gameclock.R
import com.example.gameclock.ViewModels.AlarmViewModel
import com.example.gameclock.models.Puzzle
import com.example.gameclock.models.PuzzleType
import com.example.gameclock.models.getPuzzleOptions
import com.example.gameclock.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PuzzleSelectionScreen(
    navController: NavHostController,
    alarmId: String?,
    alarmViewModel: AlarmViewModel,
    context: Context
) {
    val puzzleOptions = getPuzzleOptions()

    val selectedPuzzle by alarmViewModel.selectedPuzzle.collectAsState()

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Puzzle") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "Back Button")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                navController.popBackStack()
                            },
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            Text(text = "Select", fontSize = 16.sp)
                        }
                    }
                }
            )
        }
    ) {  innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            items(puzzleOptions) { puzzle ->
                PuzzleItem(
                    puzzle = puzzle,
                    selectedPuzzleId = selectedPuzzle?.id,
                    onPuzzleSelection = { alarmViewModel.setPuzzle(puzzle) },
                    navController = navController
                )
            }
        }

    }
}

@Composable
fun PuzzleItem(
    puzzle: Puzzle,
    selectedPuzzleId: Int?,
    onPuzzleSelection: (Int) -> Unit,
    navController: NavHostController
) {
    Row (
        Modifier
            .clickable { onPuzzleSelection(puzzle.id) }
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        RadioButton(
            selected = selectedPuzzleId == puzzle.id,
            onClick = { onPuzzleSelection(puzzle.id) }
        )
        Column (
            modifier = Modifier.weight(1f)
                .padding(start = 10.dp, end = 10.dp)
        ) {
            Text(text = puzzle.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(text = puzzle.description, fontSize = 18.sp)
            androidx.compose.material.Button(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .width(100.dp),

                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xff0d0c0b),
                ),
                onClick = {
                    navController.navigate(Screen.PuzzlePreviewScreen.withId(puzzle.id.toString()))
                }
            ) {
                Text(
                    text = "Preview",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }

        Icon(painter = painterResource(id = puzzle.icon), contentDescription = "Puzzle Icon")


    }
}