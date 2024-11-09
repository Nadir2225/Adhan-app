package com.example.adhan.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.adhan.ui.viewModels.AdhanViewModel

@Composable
fun PrayerTimesScreen(adhanViewModel: AdhanViewModel) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "prayer time", modifier = Modifier.align(Alignment.Center))
        Button(onClick = {
            adhanViewModel.scheduleAdhanAlarm(context, 5L)
        }, modifier = Modifier.align(Alignment.Center)
        ) {
            Text("Schedule Adhan in 30 Seconds")
        }
    }
}