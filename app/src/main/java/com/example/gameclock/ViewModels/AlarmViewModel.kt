package com.example.gameclock.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.gameclock.Repository.AlarmRepository
import com.example.gameclock.models.Alarm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.util.Date

class AlarmViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AlarmRepository(application)
    private val _alarms = MutableStateFlow<List<Alarm>>(emptyList())
    val alarms: StateFlow<List<Alarm>> get() = _alarms

    private val _selectedRingtone = MutableStateFlow<String?>(null)
    val selectedRingtone: StateFlow<String?> get() = _selectedRingtone

    private val _selectedHour =  MutableStateFlow(12)
    val selectedHour: StateFlow<Int> get() = _selectedHour

    private val _selectedMinute = MutableStateFlow(30)
    val selectedMinute : StateFlow<Int> get() = _selectedMinute

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate : MutableStateFlow<LocalDate> get() = _selectedDate

    fun setHour(hour: Int) {
        _selectedHour.value = hour
    }

    fun setMinute(minute: Int) {
        _selectedMinute.value = minute
    }

    fun setDate(date: LocalDate) {
        _selectedDate.value = date
    }

    fun setRingtone(ringtone: String) {
        _selectedRingtone.value = ringtone
    }

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