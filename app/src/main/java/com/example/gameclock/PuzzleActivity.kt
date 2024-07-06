package com.example.gameclock

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModelProvider
import com.example.gameclock.ui.theme.GameClockTheme
import com.example.gameclock.ViewModels.AlarmViewModel
import com.example.gameclock.helper.AlarmForegroundService
import com.example.gameclock.helper.MediaPlayerHelper
import com.example.gameclock.models.Alarm
import kotlinx.coroutines.delay



class PuzzleActivity : ComponentActivity() {
    private lateinit var alarmViewModel: AlarmViewModel
    private var alarmId: String? = null
    private val notificationId = 1 //  matches the notification ID used in AlarmForegroundService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmId = intent.getStringExtra("alarmId")
        alarmViewModel = ViewModelProvider(this)[AlarmViewModel::class.java]
        val alarm: Alarm? = alarmViewModel.alarms.value.find { alarm -> alarmId == alarm.id }
        Log.i("PuzzleActivity", alarm.toString())

        setContent {
            GameClockTheme {
                if (alarm != null) {
                    var showEmergencyButton by remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        delay(10000)
                        showEmergencyButton = true
                    }

                    alarm.puzzle?.DisplayPuzzle(
                        alarmId = alarmId ?: "",
                        onPuzzleSolved = {
                            // Stop the MediaPlayer when the puzzle is solved
                            MediaPlayerHelper.stop()
                            // Remove the alarm from the view model
                            if (alarm.recurringDays.isEmpty()) {
                                alarmId?.let {
                                    alarmViewModel.removeAlarmById(it)
                                }
                            }
                            stopAlarmForegroundService()
                            cancelNotification()

                            finish()
                        },
                        onEmergencyStop = {

                            // Stop the MediaPlayer when emergency stop is pressed
                            MediaPlayerHelper.stop()
                            // Remove the alarm from the view model
                            if (alarm.recurringDays.isEmpty()) {
                                alarmId?.let {
                                    alarmViewModel.removeAlarmById(it)
                                }
                            }
                            stopAlarmForegroundService()
                            cancelNotification()

                            finish()
                        },
                        showEmergencyButton = showEmergencyButton
                    )
                }
            }
        }
    }

    private fun cancelNotification() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)
        Log.i("PuzzleActivity", "Notification cancelled")
    }

    private fun stopAlarmForegroundService() {
        val intent = Intent(this, AlarmForegroundService::class.java)
        stopService(intent)
        Log.i("PuzzleActivity", "AlarmForegroundService stopped")
    }
}