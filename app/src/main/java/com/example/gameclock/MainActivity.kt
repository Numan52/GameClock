package com.example.gameclock

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import com.example.gameclock.navigation.Navigation
import com.example.gameclock.ui.theme.GameClockTheme
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.core.content.ContextCompat
import com.example.gameclock.models.PreviewMemoryPuzzle

class MainActivity : ComponentActivity() {
    private val permissions = listOf(
        Manifest.permission.SCHEDULE_EXACT_ALARM,
        Manifest.permission.RECORD_AUDIO
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Register the permission launcher
        val requestPermissionsLauncher = registerForActivityResult(RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                val isGranted = it.value
                if (!isGranted) {
                    // Handle the case where the user denies permission
                }
            }
        }

        // Check and request permissions
        if (!hasPermissions(permissions)) {
            requestPermissionsLauncher.launch(permissions.toTypedArray())
        }

        setContent {
            GameClockTheme {
                Navigation(applicationContext)
            }
        }
    }

    private fun hasPermissions(permissions: List<String>): Boolean {
        return permissions.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }
    }
}