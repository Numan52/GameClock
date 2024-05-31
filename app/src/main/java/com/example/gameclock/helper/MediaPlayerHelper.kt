package com.example.gameclock.helper


import android.content.Context
import android.media.MediaPlayer

object MediaPlayerHelper {
    private var mediaPlayer: MediaPlayer? = null

    fun start(context: Context, resId: Int) {
        stop() // Stop any existing MediaPlayer instance
        mediaPlayer = MediaPlayer.create(context, resId)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}