package com.example.adhan.ui.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.adhan.ui.workers.AdhanWorker
import java.util.concurrent.TimeUnit

class AdhanViewModel : ViewModel() {

    fun scheduleAdhanAlarm(context: Context, delayInMillis: Long) {
        val adhanWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<AdhanWorker>()
            .setInitialDelay(delayInMillis, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(context).enqueue(adhanWorkRequest)
    }
}