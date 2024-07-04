package com.example.gameclock.screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.gameclock.ViewModels.AlarmViewModel
import com.example.gameclock.helper.AlarmManagerHelper
import com.example.gameclock.models.Alarm
import com.example.gameclock.models.JigsawPuzzle
import com.example.gameclock.models.Puzzle
import com.example.gameclock.navigation.Screen
import com.example.gameclock.widgets.DatePicker
import com.example.gameclock.widgets.DayPicker
import com.example.gameclock.widgets.SaveCancelBar
import com.example.gameclock.widgets.TimePicker
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun AlarmDetailsScreen(
    navController: NavHostController,
    alarmId: String?,
    alarmViewModel: AlarmViewModel,
    context: Context,
    alarmManagerHelper: AlarmManagerHelper
) {
    SetAlarmScreen(navController = navController, alarmId = alarmId, alarmViewModel = alarmViewModel, context = context, alarmManagerHelper = alarmManagerHelper)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SetAlarmScreen(
    navController: NavHostController,
    alarmId: String?,
    alarmViewModel: AlarmViewModel,
    context: Context,
    alarmManagerHelper: AlarmManagerHelper
) {

    val selectedHour by alarmViewModel.selectedHour.collectAsState()
    val selectedMinute by alarmViewModel.selectedMinute.collectAsState()
    val selectedDate by alarmViewModel.selectedDate.collectAsState()
    val selectedDays by alarmViewModel.selectedDays.collectAsState()
    val selectedRingtone by alarmViewModel.selectedRingtone.collectAsState()
    val selectedPuzzle by alarmViewModel.selectedPuzzle.collectAsState()

    var selectedTimeInfo by rememberSaveable { mutableStateOf("") }

    val daysOfWeekOrder = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    if (selectedDays.isNotEmpty()) {
        if (selectedDays.size == 7) {
            selectedTimeInfo = "Daily"
        } else {
            val stringBuilder = StringBuilder("Every ")
            val sortedDays = selectedDays.sortedBy { daysOfWeekOrder.indexOf(it) }
            for (day in sortedDays) {
                stringBuilder.append(day).append(",")
            }
            selectedTimeInfo = stringBuilder.toString().removeSuffix(",")
        }
    } else {
        val dateFormatter = DateTimeFormatter.ofPattern("EEE, MMMM d, yyyy", Locale.getDefault())
        val formattedDate = selectedDate.format(dateFormatter)
        selectedTimeInfo = formattedDate
    }

    val currentDateTime = remember { Calendar.getInstance() }
    val selectedDateTime = Calendar.getInstance().apply {
        set(selectedDate.year, selectedDate.monthValue - 1, selectedDate.dayOfMonth, selectedHour, selectedMinute)
    }
    val isDateTimeInFuture = selectedDateTime.after(currentDateTime)

    Scaffold(
        bottomBar = {
            SaveCancelBar(navController = navController, onSaveClick = {
                if (isDateTimeInFuture || selectedDays.isNotEmpty()) {
                    Log.i("selected-puzzle", selectedPuzzle?.puzzleType.toString() )
                    val alarm = Alarm(
                        id = alarmId ?: UUID.randomUUID().toString(),
                        hour = selectedHour,
                        minute = selectedMinute,
                        date = if (selectedDays.isEmpty()) selectedDate else null,
                        recurringDays = selectedDays,
                        ringtone = selectedRingtone ?: "alarm1",
                        puzzle = selectedPuzzle ?: JigsawPuzzle()
                    )
                    Log.i("my-alarm", alarm.toString())
                    alarmViewModel.addAlarm(alarm)
                    alarmManagerHelper.setAlarm(alarm)
                    navController.navigate(Screen.HomeScreen.route)
                } else {
                    Toast.makeText(context, "You can't select a time in the past", Toast.LENGTH_LONG).show()
                }
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

            TimePicker(alarmViewModel = alarmViewModel, onTimeSelected = { hour, minute ->
                alarmViewModel.setHour(hour)
                alarmViewModel.setMinute(minute)
                Log.i("hour and minute", alarmViewModel.selectedHour.value.toString() + ", " + alarmViewModel.selectedMinute.value)
            })

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = selectedTimeInfo, fontSize = 18.sp)
                DatePicker(onDateSelected = { date ->
                    alarmViewModel.resetSelectedDays()
                    alarmViewModel.setDate(date)
                })
            }

            DayPicker(alarmViewModel = alarmViewModel)

            Spacer(modifier = Modifier.height(16.dp))

            AlarmTone(
                modifier = Modifier.padding(vertical = 20.dp),
                navController = navController,
                alarmViewModel = alarmViewModel,
                alarmId = alarmId ?: UUID.randomUUID().toString()
            )

            Spacer(modifier = Modifier.height(16.dp))

            selectedPuzzle?.let {
                PuzzleSelection(
                    alarmViewModel = alarmViewModel,
                    alarmId = alarmId ?: UUID.randomUUID().toString(),
                    selectedPuzzle = it,
                    navController = navController
                )
            }
        }
    }
}




@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AlarmTone(
    modifier: Modifier = Modifier,
    navController: NavController,
    alarmId: String,
    alarmViewModel: AlarmViewModel
) {
    val context = LocalContext.current
    val selectedRingtone by alarmViewModel.selectedRingtone.collectAsState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Screen.RingtoneScreen.withId(alarmId)) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = com.example.gameclock.R.drawable.baseline_music_note_24), contentDescription = "Music Icon")
        Column(
            modifier = Modifier
                .padding(start = 10.dp)
        ) {
            Text(text = "Ringtone", fontSize = 22.sp)
            Log.i("SelectedRingtone",
                alarmViewModel.selectedRingtone.value.toString()
            )
            Text(text = selectedRingtone ?: "Select Ringtone")
        }
    }
}


@Composable
fun PuzzleSelection(
    alarmViewModel: AlarmViewModel,
    alarmId: String,
    navController: NavController,
    selectedPuzzle: Puzzle
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(Screen.PuzzleSelectionScreen.withId(alarmId)) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = com.example.gameclock.R.drawable.puzzle_piece), contentDescription = "Puzzle Icon")
        Column(
            modifier = Modifier
                .padding(start = 10.dp)
        ) {
            Text(text = "Puzzle", fontSize = 22.sp)
            Text(text =  selectedPuzzle.name)
//            Text(text = selectedRingtone ?: "Select Ringtone")
        }
    }
}




