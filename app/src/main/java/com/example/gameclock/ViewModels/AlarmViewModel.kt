package com.example.gameclock.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.gameclock.Repository.AlarmRepository
import com.example.gameclock.models.Alarm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AlarmViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AlarmRepository(application)
    private val _alarms = MutableStateFlow<List<Alarm>>(emptyList())
    val alarms: StateFlow<List<Alarm>> get() = _alarms

    private val _selectedRingtone = MutableStateFlow<String?>(null)
    val selectedRingtone: StateFlow<String?> get() = _selectedRingtone

    init {
        _alarms.value = repository.loadAlarms()
    }

    fun addAlarm(alarm: Alarm) {
        _alarms.value = _alarms.value + alarm
        repository.saveAlarms(_alarms.value)
    }

    fun removeAlarm(alarm: Alarm) {
        _alarms.value = _alarms.value - alarm
        repository.saveAlarms(_alarms.value)
    }

    fun removeAlarmById(id: String) {
        val alarm = _alarms.value.find { it.id == id }
        if (alarm != null) {
            removeAlarm(alarm)
        }
    }

    fun getAlarmById(id: String): Alarm? {
        return _alarms.value.find { it.id == id }
    }

    fun setSelectedRingtone(ringtone: String) {
        _selectedRingtone.value = ringtone
    }
}