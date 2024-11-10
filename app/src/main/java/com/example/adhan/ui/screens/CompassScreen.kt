package com.example.adhan.ui.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.adhan.models.Screen
import com.example.adhan.ui.theme.dark

@Composable
fun CompassScreen(navController: NavController) {
    val sensorManager = LocalContext.current.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    // Check if the magnetic field sensor is available
    val magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    var isDialogShown by remember {
        mutableStateOf(magneticSensor == null)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dark)
    ) {
        Text(text = "this feature is coming soon", modifier = Modifier.align(Alignment.Center), color = Color.White)
        if (isDialogShown) {
            Dialog(navController = navController)
        }
    }
}

@Composable
fun Dialog(navController: NavController) {
    AlertDialog(
        onDismissRequest = {
            navController.navigate(Screen.PrayerTimes.route)
        },
        text = {
            Text("Your device does not have a compass (magnetic field sensor), we can't calculate the direction of Qibla without it.")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    navController.navigate(Screen.PrayerTimes.route)
                }
            ) {
                Text("OK")
            }
        }
    )
}