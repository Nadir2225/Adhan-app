package com.example.adhan.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PrayerTimesScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "prayer time", modifier = Modifier.align(Alignment.Center))
    }
}