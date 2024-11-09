package com.example.adhan.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.adhan.models.PrayTime
import com.example.adhan.ui.theme.dark
import com.example.adhan.ui.viewModels.LocationViewModel
import java.util.Calendar
import java.util.TimeZone

@Composable
fun PrayerTimesScreen(locationViewModel: LocationViewModel) {
    val latitude by locationViewModel.latitude.observeAsState()
    val longitude by locationViewModel.longitude.observeAsState()

    LaunchedEffect(Unit) {
        if (longitude == null || latitude == null) {

        }
    }


    // Get prayer times for the specified location and date
    val prayerTimes = latitude?.let { longitude?.let { it1 -> getPrayerTimes(it, it1) } }

    Box(modifier = Modifier.fillMaxSize().background(dark)) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(text = "Prayer Times", textAlign = TextAlign.Center)

            // Display each prayer time in the list
            Text(text = "Fajr: ${prayerTimes?.get("fajr")}")
            Text(text = "Sunrise: ${prayerTimes?.get("sunrise")}")
            Text(text = "Dhuhr: ${prayerTimes?.get("duhr")}")
            Text(text = "Asr: ${prayerTimes?.get("asr")}")
            Text(text = "Maghrib: ${prayerTimes?.get("maghrib")}")
            Text(text = "Isha: ${prayerTimes?.get("isha")}")
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