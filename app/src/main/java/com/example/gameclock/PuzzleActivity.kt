package com.example.gameclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.gameclock.screens.PuzzleScreen
import com.example.gameclock.ui.theme.GameClockTheme
import com.example.gameclock.ViewModels.AlarmViewModel
import com.example.gameclock.helper.MediaPlayerHelper

class PuzzleActivity : ComponentActivity() {
    private lateinit var alarmViewModel: AlarmViewModel
    private var alarmId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmId = intent.getStringExtra("alarmId")
        alarmViewModel = ViewModelProvider(this)[AlarmViewModel::class.java]

        setContent {
            GameClockTheme {
                PuzzleScreen(
                    alarmId = alarmId ?: "",
                    onPuzzleSolved = {
                        // Stop the MediaPlayer when the puzzle is solved
                        MediaPlayerHelper.stop()
                        // Remove the alarm from the view model
                        alarmId?.let {
                            alarmViewModel.removeAlarmById(it)
                        }
                        finish()
                    },
                    onEmergencyStop = {
                        // Stop the MediaPlayer when emergency stop is pressed
                        MediaPlayerHelper.stop()
                        // Remove the alarm from the view model
//                        alarmId?.let {
//                            alarmViewModel.removeAlarmById(it)
//                        }
                        finish()
                    }
                )
            }
        }
    }
}