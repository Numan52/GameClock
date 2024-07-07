package com.example.gameclock.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gameclock.navigation.Screen

@Composable
fun SaveCancelBar(navController: NavHostController, onSaveClick: () -> Unit) {
    BottomAppBar(
        backgroundColor = Color(0xff0d0c0b),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SaveCancelButton(text = "Cancel", onClick = { navController.navigate(Screen.HomeScreen.route) })
                SaveCancelButton(text = "Save", onClick = onSaveClick)
            }
        }
    )
}

@Composable
fun SaveCancelButton(text: String, onClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xff0d0c0b),
        ),
        onClick = {
            onClick()
        }
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}


