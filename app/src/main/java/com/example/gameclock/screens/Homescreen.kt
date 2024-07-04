package com.example.gameclock.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

import com.example.gameclock.ViewModels.AlarmViewModel
import com.example.gameclock.helper.AlarmManagerHelper
import com.example.gameclock.models.Alarm
import com.example.gameclock.navigation.Screen
import com.example.gameclock.widgets.TopAppBar



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController,
    alarmViewModel: AlarmViewModel,
    alarmManagerHelper: AlarmManagerHelper
) {
    val alarms = alarmViewModel.alarms.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Alarms") },
                backgroundColor = Color.Black,
                contentColor = Color.White
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val newAlarmId = java.util.UUID.randomUUID().toString()
                    navController.navigate(route = Screen.AlarmDetailsScreen.withId(newAlarmId))
                },
                backgroundColor = Color.Black
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Alarm", tint = Color.White)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            if (alarms.value.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "You don't have any alarms",
                        fontSize = 20.sp,
                        color = Color.Gray
                    )
                }
            } else {
                AlarmsList(
                    navController = navController,
                    alarms = alarms.value,
                    alarmViewModel = alarmViewModel,
                    alarmManagerHelper = alarmManagerHelper
                )
            }
        }
    }
}

@Composable
fun AlarmsList(
    navController: NavHostController,
    alarms: List<Alarm>,
    alarmViewModel: AlarmViewModel,
    alarmManagerHelper: AlarmManagerHelper
) {
    LazyColumn {
        items(alarms) { alarm ->
            AlarmItem(
                navController = navController,
                alarm = alarm,
                alarmViewModel = alarmViewModel,
                alarmManagerHelper = alarmManagerHelper
            )
        }

        Log.i("alarms", alarms.toString())
    }
}

@Composable
fun AlarmItem(
    navController: NavHostController,
    alarm: Alarm,
    alarmViewModel: AlarmViewModel,
    alarmManagerHelper: AlarmManagerHelper
) {
    Log.i("alarmDate", alarm.date.toString())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable {
                navController.navigate(route = Screen.AlarmDetailsScreen.withId(alarm.id))
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            val formattedHour = String.format("%02d", alarm.hour)
            val formattedMinute = String.format("%02d", alarm.minute)

            Text(text = "$formattedHour:$formattedMinute", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            if (alarm.date != null) {
                Text(text = alarm.date.toString(), fontSize = 14.sp, color = Color.Gray)
            } else if (alarm.recurringDays.isNotEmpty()) {
                val days = if (alarm.recurringDays.size == 7) {
                    "Daily"
                } else {
                    "Every " + alarm.recurringDays.joinToString(", ")
                }
                Text(text = days, fontSize = 14.sp, color = Color.Gray)
            }
        }
        IconButton(onClick = {
            alarmViewModel.removeAlarm(alarm)
            alarmManagerHelper.cancelAlarm(alarm, setOf())
        }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Alarm", tint = Color.Red)
        }
    }
}
