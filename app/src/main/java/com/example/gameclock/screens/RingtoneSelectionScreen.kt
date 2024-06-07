package com.example.gameclock.screens

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gameclock.R
import com.example.gameclock.ViewModels.AlarmViewModel
import com.example.gameclock.navigation.Screen

val ringtoneList = listOf(
    "alarm1",
    "alarm_clock_old",
    "club_alarm",
    "extreme_alarm_clock",
    "good_morning",
    "iphone_alarm",
    "samsung_ringtone",
)

@SuppressLint("DiscouragedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RingtoneSelectionScreen(
    navController: NavController,
    alarmId: String?,
    alarmViewModel: AlarmViewModel = viewModel(),
    context: Context
) {

    val selectedRingtone by alarmViewModel.selectedRingtone.collectAsState()
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    DisposableEffect(selectedRingtone) {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        selectedRingtone?.let { ringtone ->
            val resId = context.resources.getIdentifier(ringtone, "raw", context.packageName)
            mediaPlayer = MediaPlayer.create(context, resId)
            mediaPlayer?.start()
        }
        onDispose {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Ringtone") },
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
                                selectedRingtone?.let { alarmViewModel.setSelectedRingtone(it) }
                                Log.i("SelectedRingtone",
                                    alarmViewModel.selectedRingtone.value.toString()
                                )
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
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            items(ringtoneList) { ringtone ->
                RingtoneItem(
                    ringtone = ringtone,
                    selectedRingtone = selectedRingtone,
                    onRingtoneSelected = { alarmViewModel.setRingtone(ringtone) }
                )
            }
        }
    }
}

@Composable
fun RingtoneItem(
    ringtone: String,
    selectedRingtone: String?,
    onRingtoneSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onRingtoneSelected(ringtone) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = ringtone == selectedRingtone,
            onClick = { onRingtoneSelected(ringtone) }
        )
        Text(
            text = ringtone,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 8.dp)
                .clickable { onRingtoneSelected(ringtone) }
        )
    }
}