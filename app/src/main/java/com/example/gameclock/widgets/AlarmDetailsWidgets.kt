package com.example.gameclock.widgets

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.Hours
import com.chargemap.compose.numberpicker.HoursNumberPicker
import com.example.gameclock.ViewModels.AlarmViewModel
import java.time.LocalDate
import java.util.Calendar


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
        Icon(imageVector = Icons.Default.DateRange, contentDescription = "Date icon")
//        Text(text = "Selected Date: ${selectedDate.toString()}")
    }
}


// for recurrent alarms (eg. When Mon is selected, alarm will ring every monday)
@Composable
fun DayPicker(alarmViewModel: AlarmViewModel) {
    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    val selectedDays by alarmViewModel.selectedDays.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        daysOfWeek.forEach { day ->
            val isSelected = selectedDays.contains(day)
            Box(
                modifier = Modifier
                    .height(30.dp)
                    .width(50.dp)
                    .background(Color.Transparent)
                    .border(
                        BorderStroke(
                            2.dp,
                            if (isSelected) Color.Blue else Color.Transparent
                        ),
                        shape = RoundedCornerShape(5)
                    )
                    .clickable {
                        if (isSelected) {
                            alarmViewModel.removeDay(day)
                        } else {
                            alarmViewModel.addDay(day)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day,
                    fontSize = 18.sp,
                    color = if (isSelected) Color.Blue else Color.Black // Optional text color change
                )
            }
        }
    }
}

@Composable
fun TimePicker(onTimeSelected: (hour: Int, minute: Int) -> Unit, alarmViewModel: AlarmViewModel) {
    var pickerValue by remember { mutableStateOf<Hours>(FullHours(alarmViewModel.selectedHour.value, alarmViewModel.selectedMinute.value )) }

    HoursNumberPicker(
        dividersColor = MaterialTheme.colorScheme.primary,
        leadingZero = true,
        value = pickerValue,
        onValueChange = {
            pickerValue = it
            onTimeSelected(it.hours, it.minutes)
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
