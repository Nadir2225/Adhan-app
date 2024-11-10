package com.example.adhan

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.example.adhan.ui.viewModels.LocationViewModel
import com.example.adhan.ui.viewModels.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val mainViewModel: MainViewModel by viewModels()
    private val locationViewModel: LocationViewModel by viewModels()
//    private val adhanViewModel: AdhanViewModel by viewModels()
    private val adhanViewModel: AdhanViewModel
        get() = (application as MyApplication).adhanViewModel

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            AdhanTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = if (checkPermissions()) Screen.App.route else Screen.NoPermission.route
                ) {
                    composable(Screen.App.route) {
                        App(globalNavController = navController, adhanViewModel = adhanViewModel, locationViewModel = locationViewModel) {
                            checkPermissions()
                        }
                    }
                    composable(Screen.NoPermission.route) {
                        NoPermissionScreen(mainViewModel = mainViewModel, navController = navController, permissions = permissions) {
                            checkPermissions()
                        }
                    }
                }
            }
        }
    }
}