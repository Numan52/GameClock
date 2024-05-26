package com.example.gameclock.widgets

import android.graphics.ColorSpace.Rgb
import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SaveCancelBar() {
    BottomAppBar(
        backgroundColor = Color(0xff0d0c0b),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp)
                ,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SaveCancelButton(text = "Cancel", onClick = { println("Cancel Button clicked") })
                SaveCancelButton(text = "Save", onClick = { println("Save Button clicked") })
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
            TODO("Handle click")
        })
    {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}
