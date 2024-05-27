package com.example.gameclock.screens

import android.annotation.SuppressLint
import android.icu.lang.UCharacter.VerticalOrientation
import android.media.MediaPlayer
import android.media.Ringtone
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.example.gameclock.R
import com.example.gameclock.ViewModels.AlarmViewModel
import com.example.gameclock.widgets.TopAppBar


val ringtoneList = listOf(
    "alarm1",
    "alarm_clock_old",
    "club_alarm",
    "extreme_alarm_clock",
    "good_morning",
    "iphone_alarm",
    "samsung_ringtone",
)



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "DiscouragedApi")
@Composable
fun RingtoneSelectionScreen(
    navController: NavController,
    alarmId: String?,
    alarmViewModel: AlarmViewModel) {

    val (selectedRingtone, setSelectedRingtone) = remember{ mutableStateOf<String?>(null) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(selectedRingtone) {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        selectedRingtone?.let { ringtone ->
            val resId = context.resources.getIdentifier(ringtone, "raw", context.packageName)
            mediaPlayer = MediaPlayer.create(context, resId)
            mediaPlayer?.start()
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer = null
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(title = "Ringtone", navigationIcons = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Back Button")
                }
            })
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
                    onRingtoneSelected = { setSelectedRingtone(ringtone) }

                )

            }
        }
    }

}

@Composable
fun RingtoneItem(
    ringtone: String,
    selectedRingtone: String?,
    onRingtoneSelected: (String) -> Unit) {
    Row(

        modifier = Modifier

            .clickable { onRingtoneSelected(ringtone) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = ringtone == selectedRingtone,
            onClick = { onRingtoneSelected(ringtone) })
        Text(
            text = ringtone,
            modifier = Modifier.clickable { onRingtoneSelected(ringtone) },
            fontSize = 20.sp,
        )
    }
}


