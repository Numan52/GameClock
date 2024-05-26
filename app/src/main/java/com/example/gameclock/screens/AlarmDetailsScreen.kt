package com.example.gameclock.screens

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.chargemap.compose.numberpicker.HoursNumberPicker
import com.example.gameclock.AlarmReceiver
import com.example.gameclock.ViewModels.AlarmViewModel
import com.example.gameclock.widgets.SaveCancelBar
import java.util.Calendar


@Composable
fun AlarmDetailsScreen(
    navController: NavHostController,
    alarmId: String?,
    alarmViewModel: AlarmViewModel
) {
    SetAlarmScreen(navController = navController, alarmId = alarmId, alarmViewModel = alarmViewModel)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SetAlarmScreen(
    navController: NavHostController,
    alarmId: String?,
    alarmViewModel: AlarmViewModel
) {

    Scaffold(
        bottomBar = {
            SaveCancelBar(navController = navController)
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            TimePicker()

            Spacer(modifier = Modifier.height(16.dp))

        }
    }

}

@Composable
fun TimePicker() {
    var pickerValue by remember { mutableStateOf<Hours>(FullHours(12, 30)) }
    Log.d("Hour", pickerValue.hours.toString())
    HoursNumberPicker(

        dividersColor = MaterialTheme.colorScheme.primary,
        leadingZero = false,
        value = pickerValue,
        onValueChange = {
            pickerValue = it
        },
        textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold),
        hoursDivider = {
            Text(
                modifier = Modifier.size(24.dp),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                text = ":"
            )
        }
    )
}



@SuppressLint("ScheduleExactAlarm")
private fun setAlarm(context: Context, hour: Int, minute: Int) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
    }

    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )
}


