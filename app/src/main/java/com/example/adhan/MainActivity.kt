package com.example.adhan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.adhan.models.Screen
import com.example.adhan.ui.screens.CompassScreen
import com.example.adhan.ui.screens.NoPermissionScreen
import com.example.adhan.ui.screens.PrayerTimesScreen
import com.example.adhan.ui.screens.SettingsScreen
import com.example.adhan.ui.theme.AdhanTheme
import com.example.adhan.ui.viewModels.AdhanViewModel
import com.example.adhan.ui.viewModels.MainViewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val adhanViewModel: AdhanViewModel by viewModels()


    private val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        mapOf(
            "location" to Manifest.permission.ACCESS_COARSE_LOCATION,
            "notifications" to Manifest.permission.POST_NOTIFICATIONS
        )
    } else {
        mapOf(
            "location" to Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private fun checkPermissions(): Boolean {
        var granted = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (permissions["notifications"]?.let { checkSelfPermission(it) } != PackageManager.PERMISSION_GRANTED) {
                granted = false
            }
        }
        if (permissions["location"]?.let { checkSelfPermission(it) } != PackageManager.PERMISSION_GRANTED) {
            granted = false
        }
        return granted
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdhanTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = if (checkPermissions()) Screen.App.route else Screen.NoPermission.route
                ) {
                    composable(Screen.App.route) { App(navController, adhanViewModel, { checkPermissions() }) }
                    composable(Screen.NoPermission.route) { NoPermissionScreen(mainViewModel = mainViewModel, navController = navController, permissions = permissions,
                        { checkPermissions() }) }
                }
            }
        }
    }
}