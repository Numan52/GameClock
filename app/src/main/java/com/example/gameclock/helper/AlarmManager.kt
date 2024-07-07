package com.example.gameclock.helper

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.gameclock.AlarmReceiver
import com.example.gameclock.models.Alarm
import java.util.*

class AlarmManagerHelper(private val context: Context) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // Set an alarm
    @SuppressLint("ScheduleExactAlarm")
    fun setAlarm(alarm: Alarm) {
        // Create an intent to trigger AlarmReceiver
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("alarmId", alarm.id)
            putExtra("ringtone", alarm.ringtone) // Pass the selected ringtone
        }

        // Recurring alarm
        if (alarm.recurringDays.isNotEmpty()) {
            val daysOfWeekOrder = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

            // Set an alarm for each recurring day
            alarm.recurringDays.forEach { day ->
                val dayOfWeek = daysOfWeekOrder.indexOf(day) + 1
                val nextAlarmTime = getNextAlarmTime(dayOfWeek, alarm.hour, alarm.minute)
                Log.i("AlarmManagerHelper", "Setting recurring alarm for $day at ${nextAlarmTime.time}")
                Log.i("Alarm", alarm.toString())
                val daySpecificIntent = PendingIntent.getBroadcast(
                    context,
                    (alarm.id.hashCode() + dayOfWeek), // Unique request code for each day
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )

                // Set the alarm to repeat weekly
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    nextAlarmTime.timeInMillis,
                    AlarmManager.INTERVAL_DAY * 7, // Repeat weekly
                    daySpecificIntent
                )
            }

        } else { // If the alarm is set for a single day
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                alarm.id.hashCode(),
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            // Set the alarm time
            val calendar = Calendar.getInstance().apply {
                alarm.date?.let { set(it.year, alarm.date.monthValue - 1, alarm.date.dayOfMonth, alarm.hour, alarm.minute, 0) }
            }
            Log.i("AlarmManagerHelper", "Setting single alarm for ${calendar.time}")
            // Set the alarm to trigger at the exact time
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
    }

    fun cancelAlarm(alarm: Alarm, selectedDays: Set<String>) {
        val intent = Intent(context, AlarmReceiver::class.java)


        // for multiple days, cancel each day's alarm
        if (selectedDays.isNotEmpty()) {
            val daysOfWeekOrder = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
            selectedDays.forEach { day ->
                val dayOfWeek = daysOfWeekOrder.indexOf(day) + 1
                val daySpecificIntent = PendingIntent.getBroadcast(
                    context,
                    (alarm.id.hashCode() + dayOfWeek),
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
                alarmManager.cancel(daySpecificIntent)
            }
        }
        else { // for a single day, cancel the alarm
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                alarm.id.hashCode(),
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager.cancel(pendingIntent)
        }
    }

    // Calculate the next time the alarm should go off
    private fun getNextAlarmTime(dayOfWeek: Int, hour: Int, minute: Int): Calendar {

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        // Calculate days difference to the next alarm day
        var daysDifference = dayOfWeek - (calendar.get(Calendar.DAY_OF_WEEK) - 1) // -1, because DAY_OF_WEEK returns 2 for monday but it should be 1
        if (daysDifference < 0) { // day in past - go to next week
            daysDifference += 7
        } else if (daysDifference == 0 && calendar.timeInMillis < System.currentTimeMillis()) {
            daysDifference = 7
        }

        calendar.add(Calendar.DAY_OF_MONTH, daysDifference)

        return calendar
    }
}