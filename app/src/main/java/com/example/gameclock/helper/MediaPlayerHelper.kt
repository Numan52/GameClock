package com.example.gameclock.helper


import android.content.Context
import android.media.MediaPlayer

// Helper object to manage media playback
object MediaPlayerHelper {
    private var mediaPlayer: MediaPlayer? = null

    // Start playing a sound
    fun start(context: Context, resId: Int) {
        stop() // Stop any existing MediaPlayer instance
        mediaPlayer = MediaPlayer.create(context, resId)
        mediaPlayer?.isLooping = true // Loop the sound
        mediaPlayer?.start()
    }

    // Stop playing the sound
    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}