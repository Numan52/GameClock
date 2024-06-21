package com.example.gameclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModelProvider
import com.example.gameclock.ui.theme.GameClockTheme
import com.example.gameclock.ViewModels.AlarmViewModel
import com.example.gameclock.helper.MediaPlayerHelper
import com.example.gameclock.models.Alarm

class PuzzleActivity : ComponentActivity() {
    private lateinit var alarmViewModel: AlarmViewModel
    private var alarmId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmId = intent.getStringExtra("alarmId")
        alarmViewModel = ViewModelProvider(this)[AlarmViewModel::class.java]
        val alarm: Alarm? =  alarmViewModel.alarms.value.find { alarm -> alarmId == alarm.id }


        setContent {
            GameClockTheme {
                if (alarm != null) {
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
                            finish()
                        }
                    )
                }
            }
        }
    }
}