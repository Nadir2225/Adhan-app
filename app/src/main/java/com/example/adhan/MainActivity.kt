package com.example.adhan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import com.example.adhan.ui.viewModels.MainViewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
//    ACCESS_COARSE_LOCATION
    private val permission = Manifest.permission.ACCESS_COARSE_LOCATION

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdhanTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) Screen.App.route else Screen.NoPermission.route
                ) {
                    composable(Screen.App.route) { App(navController) }
                    composable(Screen.NoPermission.route) { NoPermissionScreen(mainViewModel = mainViewModel, navController = navController, permission = permission) }
                }
            }
        }
    }
}