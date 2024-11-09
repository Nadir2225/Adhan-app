package com.example.adhan.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.adhan.models.PrayTime
import java.util.Calendar
import java.util.TimeZone

@Composable
fun PrayerTimesScreen() {
    // Example coordinates (latitude and longitude for your location)
    val latitude = 34.0084 // Example: New York
    val longitude = -6.8539 // Example: New York
    val currentDate = "2024-11-09" // Example: today's date
    val timezone = -5.0 // Example timezone (e.g., New York time is UTC -5)

    // Get prayer times for the specified location and date
    val prayerTimes = getPrayerTimes(latitude, longitude)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(text = "Prayer Times", textAlign = TextAlign.Center)

            // Display each prayer time in the list
            Text(text = "Fajr: ${prayerTimes["fajr"]}")
            Text(text = "Sunrise: ${prayerTimes["sunrise"]}")
            Text(text = "Dhuhr: ${prayerTimes["duhr"]}")
            Text(text = "Asr: ${prayerTimes["asr"]}")
            Text(text = "Maghrib: ${prayerTimes["maghrib"]}")
            Text(text = "Isha: ${prayerTimes["isha"]}")
        }
    }
}

fun getPrayerTimes(latitude: Double, longitude: Double): Map<String, String> {
    val timezone = TimeZone.getDefault().getOffset(System.currentTimeMillis()) / 3600000.0

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.set(year, month, day)

    val prayTime = PrayTime()

    prayTime.setLat(latitude)
    prayTime.setLng(longitude)
    prayTime.setTimeZone(timezone)
    prayTime.setCalcMethod(prayTime.mwl)

    // Get the prayer times for the given date
    val prayerTimes = prayTime.getPrayerTimes(calendar, latitude, longitude, timezone)

    // Map the prayer times to their corresponding names
    val prayerTimesMap = mutableMapOf(
        "fajr" to prayerTimes[0],
        "sunrise" to prayerTimes[1],
        "duhr" to prayerTimes[2],
        "asr" to prayerTimes[3],
        "maghrib" to prayerTimes[4],
        "isha" to prayerTimes[6]
    )

    return prayerTimesMap
}