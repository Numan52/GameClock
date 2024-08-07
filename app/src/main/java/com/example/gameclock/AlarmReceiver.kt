package com.example.gameclock

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.gameclock.helper.AlarmForegroundService
import com.example.gameclock.helper.MediaPlayerHelper

class AlarmReceiver : BroadcastReceiver() {
    @SuppressLint("DiscouragedApi", "ObsoleteSdkInt")
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

                    // Start the ForegroundService to launch the PuzzleActivity
                    val serviceIntent = Intent(context, AlarmForegroundService::class.java).apply {
                        putExtra("alarmId", alarmId)
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        context.startForegroundService(serviceIntent)
                    } else {
                        context.startService(serviceIntent)
                    }
                    Log.d("AlarmReceiver", "ForegroundService started to launch PuzzleActivity")
                } else {
                    Log.e("AlarmReceiver", "Ringtone resource not found.")
                }
            } catch (e: Exception) {
                Log.e("AlarmReceiver", "Error playing ringtone: ${e.message}")
            }
        }
    }
}