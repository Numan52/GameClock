package com.example.gameclock.screens

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.chargemap.compose.numberpicker.HoursNumberPicker
import com.example.gameclock.AlarmReceiver
import com.example.gameclock.ViewModels.AlarmViewModel
import com.example.gameclock.models.Alarm
import com.example.gameclock.navigation.Screen
import com.example.gameclock.widgets.SaveCancelBar
import java.time.LocalDate
import java.util.*

@Composable
fun AlarmDetailsScreen(
    navController: NavHostController,
    alarmId: String?,
    alarmViewModel: AlarmViewModel,
    context: Context
) {
    SetAlarmScreen(navController = navController, alarmId = alarmId, alarmViewModel = alarmViewModel, context = context)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SetAlarmScreen(
    navController: NavHostController,
    alarmId: String?,
    alarmViewModel: AlarmViewModel,
    context: Context
) {
    var selectedHour by remember { mutableStateOf(12) }
    var selectedMinute by remember { mutableStateOf(30) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val selectedRingtone by alarmViewModel.selectedRingtone.collectAsState()
    var selectedPuzzle by remember { mutableStateOf("simplePuzzle") }

    Scaffold(
        bottomBar = {
            SaveCancelBar(navController = navController, onSaveClick = {
                val alarm = Alarm(alarmId ?: UUID.randomUUID().toString(), selectedHour, selectedMinute, selectedDate, selectedRingtone ?: "alarm1", selectedPuzzle)
                alarmViewModel.addAlarm(alarm)
                setAlarm(context, alarm)
                navController.navigate(Screen.HomeScreen.route)
            })
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            TimePicker(onTimeSelected = { hour, minute ->
                selectedHour = hour
                selectedMinute = minute
            })

            Spacer(modifier = Modifier.height(16.dp))

            DatePicker(onDateSelected = { date ->
                selectedDate = date
            })

            Spacer(modifier = Modifier.height(16.dp))

            AlarmTone(
                modifier = Modifier.padding(vertical = 20.dp),
                navController = navController,
                alarmId = alarmId ?: UUID.randomUUID().toString()
            )
        }
    }
}

@Composable
fun TimePicker(onTimeSelected: (hour: Int, minute: Int) -> Unit) {
    var pickerValue by remember { mutableStateOf<Hours>(FullHours(12, 30)) }
    HoursNumberPicker(
        dividersColor = MaterialTheme.colorScheme.primary,
        leadingZero = false,
        value = pickerValue,
        onValueChange = {
            pickerValue = it
            onTimeSelected(it.hours, it.minutes)
        },
        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold),
        hoursDivider = {
            Text(
                modifier = Modifier.size(24.dp),
                fontWeight = FontWeight.SemiBold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                text = ":"
            )
        }
    )
}

@Composable
fun DatePicker(onDateSelected: (date: LocalDate) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    var selectedDate by remember { mutableStateOf(LocalDate.of(year, month + 1, day)) }
    val datePickerDialog = DatePickerDialog(context, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
        selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
        onDateSelected(selectedDate)
    }, year, month, day)

    Box(
        modifier = Modifier
            .clickable { datePickerDialog.show() }
            .padding(16.dp)
    ) {
        Text(text = "Selected Date: ${selectedDate.toString()}")
    }
}

@Composable
fun AlarmTone(
    modifier: Modifier = Modifier,
    navController: NavController,
    alarmId: String
) {
    val context = LocalContext.current
    val alarmViewModel = viewModel<AlarmViewModel>()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Screen.RingtoneScreen.withId(alarmId)) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = com.example.gameclock.R.drawable.baseline_music_note_24), contentDescription = "Music Icon")
        Column(
            modifier = Modifier
                .padding(start = 5.dp)
        ) {
            Text(text = "Ringtone", fontSize = 22.sp)
            Text(text = alarmViewModel.selectedRingtone.collectAsState().value ?: "Select Ringtone")
        }
    }
}

@SuppressLint("ScheduleExactAlarm")
fun setAlarm(context: Context, alarm: Alarm) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("alarmId", alarm.id)
        putExtra("ringtone", alarm.ringtone) // Pass the selected ringtone
    }
    val pendingIntent = PendingIntent.getBroadcast(context, alarm.id.hashCode(), intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

    val calendar = Calendar.getInstance().apply {
        set(alarm.date.year, alarm.date.monthValue - 1, alarm.date.dayOfMonth, alarm.hour, alarm.minute, 0)
    }

    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
}

fun cancelAlarm(context: Context, alarm: Alarm) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, alarm.id.hashCode(), intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
    alarmManager.cancel(pendingIntent)
}