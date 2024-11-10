package com.example.adhan.ui.screens

import android.content.Context
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.adhan.R
import com.example.adhan.models.PrayTime
import com.example.adhan.models.Screen
import com.example.adhan.ui.theme.dark
import com.example.adhan.ui.viewModels.LocationViewModel
import java.util.Calendar
import java.util.TimeZone

@Composable
fun PrayerTimesScreen(locationViewModel: LocationViewModel) {
    val latitude by locationViewModel.latitude.observeAsState()
    val longitude by locationViewModel.longitude.observeAsState()

    val context = LocalContext.current

    fun isGPSEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    var isGPSDialogShown by remember {
        mutableStateOf(!isGPSEnabled())
    }

    LaunchedEffect(Unit) {
        if (!isGPSEnabled()) {
            isGPSDialogShown = true
        }
    }

    var prayerTimes = latitude?.let { longitude?.let { it1 -> getPrayerTimes(it, it1) } }

    // Get prayer times for the specified location and date

    Box(modifier = Modifier
        .fillMaxSize()
        .background(dark)) {
        Image(painter = painterResource(R.drawable.adhan_bg2), contentDescription = null,
            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillHeight)
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80000000)))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Prayer Times", fontSize = 25.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(60.dp))
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(dark)
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Fajr")
                    Text(text = "${prayerTimes?.get("fajr")}")
                }
                Spacer(modifier = Modifier.height(3.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(dark)
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "sunrise")
                    Text(text = "${prayerTimes?.get("sunrise")}")
                }
                Spacer(modifier = Modifier.height(3.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(dark)
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Duhr")
                    Text(text = "${prayerTimes?.get("duhr")}")
                }
                Spacer(modifier = Modifier.height(3.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(dark)
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Asr")
                    Text(text = "${prayerTimes?.get("asr")}")
                }
                Spacer(modifier = Modifier.height(3.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(dark)
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Maghrib")
                    Text(text = "${prayerTimes?.get("maghrib")}")
                }
                Spacer(modifier = Modifier.height(3.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(dark)
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Icha")
                    Text(text = "${prayerTimes?.get("isha")}")
                }
            }
            // Display each prayer time in the list
//            Text(text = "Fajr: ${prayerTimes?.get("fajr")}")
//            Text(text = "Sunrise: ${prayerTimes?.get("sunrise")}")
//            Text(text = "Dhuhr: ${prayerTimes?.get("duhr")}")
//            Text(text = "Asr: ${prayerTimes?.get("asr")}")
//            Text(text = "Maghrib: ${prayerTimes?.get("maghrib")}")
//            Text(text = "Isha: ${prayerTimes?.get("isha")}")
        }
        if (isGPSDialogShown) {
            GPSDialog {
                if (isGPSEnabled()) {
                    isGPSDialogShown = false
                } else {
                    Toast.makeText(context, "please enable the gps before you click done.", Toast.LENGTH_SHORT).show()
                }
            }
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

@Composable
fun GPSDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        text = {
            Text(
                text ="your need to enable gps in order to get the prayer times correctly.\n(this dialog wont disappear until u turn on the gps)",
                textAlign = TextAlign.Center,
                color = Color.White
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Done")
            }
        },
        containerColor = dark
    )
}