package com.example.gameclock.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gameclock.ViewModels.AlarmViewModel
import com.example.gameclock.models.Alarm
import com.example.gameclock.navigation.Screen
import com.example.gameclock.widgets.TopAppBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavHostController, alarmViewModel: AlarmViewModel) {
    val alarms = alarmViewModel.alarms.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar("My Alarms")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 30.dp),
        ) {
            if (alarms.value.isEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "You don't have any alarms", fontSize = 32.sp)
                }
            } else {
                AlarmsList(navController = navController, alarms = alarms.value, alarmViewModel = alarmViewModel)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, end = 10.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add alarm",
                    modifier = Modifier
                        .clickable {
                            // Generate a unique ID for the new alarm
                            val newAlarmId = java.util.UUID.randomUUID().toString()
                            navController.navigate(route = Screen.AlarmDetailsScreen.withId(newAlarmId))
                        }
                        .size(40.dp)
                )
            }
        }
    }
}

@Composable
fun AlarmsList(navController: NavHostController, alarms: List<Alarm>, alarmViewModel: AlarmViewModel) {
    Column {
        alarms.forEach { alarm ->
            AlarmItem(navController = navController, alarm = alarm, alarmViewModel = alarmViewModel)
        }
    }
}

@Composable
fun AlarmItem(navController: NavHostController, alarm: Alarm, alarmViewModel: AlarmViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
//            .clickable {
//                navController.navigate(route = Screen.AlarmDetailsScreen.withId(alarm.id))
//            }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = "${alarm.hour}:${alarm.minute}", fontSize = 24.sp)
            Text(text = alarm.date.toString(), fontSize = 18.sp)
        }
        IconButton(onClick = {
            alarmViewModel.removeAlarm(alarm)
            cancelAlarm(navController.context, alarm)
        }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Alarm")
        }
    }
}