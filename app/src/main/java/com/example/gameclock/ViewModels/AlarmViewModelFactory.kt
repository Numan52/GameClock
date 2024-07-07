package com.example.gameclock.ViewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// create an instance of AlarmViewModel
class AlarmViewModelFactory(private val application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Check if the ViewModel class is assignable from AlarmViewModel
        if (modelClass.isAssignableFrom(AlarmViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlarmViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}