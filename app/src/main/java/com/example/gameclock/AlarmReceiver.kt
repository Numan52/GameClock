package com.example.gameclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.gameclock.helper.MediaPlayerHelper

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val alarmId = intent.getStringExtra("alarmId")
        val ringtone = intent.getStringExtra("ringtone")

        Log.d("AlarmReceiver", "Alarm triggered with ID: $alarmId and ringtone: $ringtone")

        // Play the selected ringtone
        ringtone?.let {
            try {
                val resId = context.resources.getIdentifier(it, "raw", context.packageName)
                if (resId != 0) {
                    MediaPlayerHelper.start(context, resId)

                    // Start the PuzzleActivity
                    val puzzleIntent = Intent(context, PuzzleActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        putExtra("alarmId", alarmId)
                    }
                    context.startActivity(puzzleIntent)
                } else {
                    Log.e("AlarmReceiver", "Ringtone resource not found.")
                }
            } catch (e: Exception) {
                Log.e("AlarmReceiver", "Error playing ringtone: ${e.message}")
            }
        }
    }
}