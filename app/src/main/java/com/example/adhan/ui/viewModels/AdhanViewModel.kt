package com.example.adhan.ui.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.adhan.R
import com.example.adhan.models.Screen
import com.example.adhan.ui.workers.AdhanWorker
import java.util.concurrent.TimeUnit

class AdhanViewModel : ViewModel() {
    private val _adhan: MutableLiveData<Int> = MutableLiveData(R.raw.a1)
    val adhan: LiveData<Int> = _adhan

    private val _route: MutableLiveData<String> = MutableLiveData(Screen.PrayerTimes.route)
    val route: LiveData<String> = _route

    fun scheduleAdhanAlarm(context: Context, delayInMillis: Long) {
        val adhanWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<AdhanWorker>()
            .setInitialDelay(delayInMillis, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(context).enqueue(adhanWorkRequest)
    }

    fun updateAdhan(newSheikh: Int) {
        _adhan.value = newSheikh
    }

    fun updateRoute(newRoute: String) {
        _route.value = newRoute
    }
}