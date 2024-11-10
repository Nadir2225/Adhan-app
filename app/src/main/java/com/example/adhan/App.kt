package com.example.adhan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
import com.example.adhan.ui.theme.dark
import com.example.adhan.ui.viewModels.AdhanViewModel
import com.example.adhan.ui.viewModels.LocationViewModel


@Composable
fun App(globalNavController: NavController, adhanViewModel: AdhanViewModel, locationViewModel: LocationViewModel, checkPermissions: () -> Boolean) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val route by adhanViewModel.route.observeAsState()

    LaunchedEffect(Unit) {
        if (!checkPermissions()) {
            globalNavController.navigate(Screen.NoPermission.route) {
                popUpTo(0)
            }
        } else {
            locationViewModel.getLocation()
        }
    }

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController, adhanViewModel = adhanViewModel) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = route?:Screen.PrayerTimes.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Compass.route) { CompassScreen(navController = navController) }
            composable(Screen.PrayerTimes.route) { PrayerTimesScreen(locationViewModel = locationViewModel) }
            composable(Screen.Settings.route) { SettingsScreen(adhanViewModel = adhanViewModel) }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, adhanViewModel: AdhanViewModel) {
    val route by adhanViewModel.route.observeAsState()

    val selected = remember {
        when (route) {
            Screen.PrayerTimes.route -> mutableStateOf(Icons.Filled.DateRange)
            Screen.Compass.route -> mutableStateOf(Icons.Filled.LocationOn)
            Screen.Settings.route -> mutableStateOf(Icons.Filled.Settings)
            else -> mutableStateOf(Icons.Filled.DateRange)
        }
    }

    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        content = {
            IconButton(onClick = {
                selected.value = Icons.Filled.DateRange
                adhanViewModel.updateRoute(Screen.PrayerTimes.route)
                navController.navigate(Screen.PrayerTimes.route) {
                    popUpTo(0)
                }
            }, modifier = Modifier.weight(1f)) {
                Icon( Icons.Filled.DateRange, "prayer time",
                    tint = if (selected.value == Icons.Filled.DateRange) Color(0xFFCE8920) else Color.White)
            }
            IconButton(onClick = {
                selected.value = Icons.Filled.LocationOn
                adhanViewModel.updateRoute(Screen.Compass.route)
                navController.navigate(Screen.Compass.route) {
                    popUpTo(0)
                }
            }, modifier = Modifier.weight(1f)) {
                Icon( Icons.Filled.LocationOn, "compass",
                    tint = if (selected.value == Icons.Filled.LocationOn) Color(0xFFCE8920) else Color.White)
            }
            IconButton(onClick = {
                selected.value = Icons.Filled.Settings
                adhanViewModel.updateRoute(Screen.Settings.route)
                navController.navigate(Screen.Settings.route) {
                    popUpTo(0)
                }
            }, modifier = Modifier.weight(1f)) {
                Icon( Icons.Filled.Settings, "settings",
                    tint = if (selected.value == Icons.Filled.Settings) Color(0xFFCE8920) else Color.White)
            }
        },
        containerColor = Color(0xff424242)
    )
}