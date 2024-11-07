package com.example.adhan

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.adhan.models.Screen
import com.example.adhan.ui.screens.CompassScreen
import com.example.adhan.ui.screens.NoPermissionScreen
import com.example.adhan.ui.screens.PrayerTimesScreen
import com.example.adhan.ui.screens.SettingsScreen



@Composable
fun App(globalNavController: NavController) {
    val navController = rememberNavController()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        if (checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            globalNavController.navigate(Screen.NoPermission.route) {
                popUpTo(0)
            }
        }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.PrayerTimes.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Compass.route) { CompassScreen() }
            composable(Screen.PrayerTimes.route) { PrayerTimesScreen() }
            composable(Screen.Settings.route) { SettingsScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val selected = remember {
        mutableStateOf(Icons.Filled.LocationOn)
    }
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFF1D1D1D)),
        content = {
            IconButton(onClick = {
                selected.value = Icons.Filled.DateRange
                navController.navigate(Screen.PrayerTimes.route) {
                    popUpTo(0)
                }
            }, modifier = Modifier.weight(1f)) {
                Icon( Icons.Filled.DateRange, "prayer time",
                    tint = if (selected.value == Icons.Filled.DateRange) Color(0xFFCE8920) else Color.White)
            }
            IconButton(onClick = {
                selected.value = Icons.Filled.LocationOn
                navController.navigate(Screen.Compass.route) {
                    popUpTo(0)
                }
            }, modifier = Modifier.weight(1f)) {
                Icon( Icons.Filled.LocationOn, "compass",
                    tint = if (selected.value == Icons.Filled.LocationOn) Color(0xFFCE8920) else Color.White)
            }
            IconButton(onClick = {
                selected.value = Icons.Filled.Settings
                navController.navigate(Screen.Settings.route) {
                    popUpTo(0)
                }
            }, modifier = Modifier.weight(1f)) {
                Icon( Icons.Filled.Settings, "settings",
                    tint = if (selected.value == Icons.Filled.Settings) Color(0xFFCE8920) else Color.White)
            }
        }
    )
}