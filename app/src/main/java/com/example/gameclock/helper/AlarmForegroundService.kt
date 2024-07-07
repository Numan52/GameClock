package com.example.gameclock.helper

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.gameclock.PuzzleActivity
import com.example.gameclock.R

class AlarmForegroundService : Service() { //to show a notification when an alarm rings
    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val alarmId = intent?.getStringExtra("alarmId")
        val notificationId = 1 // Unique ID for the notification
        val channelId = "AlarmForegroundServiceChannel" // Unique ID for the notification channel

        // Create a notification channel for Android O and higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Alarm Foreground Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }


        // Create an intent to launch PuzzleActivity when the notification is tapped
        val puzzleIntent = Intent(this, PuzzleActivity::class.java)
        puzzleIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        puzzleIntent.putExtra("alarmId", alarmId)


        // Create a pending intent to wrap the puzzle intent
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            puzzleIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build the notification
        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Alarm Ringing")
            .setContentText("Tap to solve the puzzle")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        // Start the service in the foreground with the notification
        startForeground(notificationId, notification)
        Log.i("ForegroundService", "yoyo")
        return START_NOT_STICKY
    }

    // required by the Service class but we do not use it
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}