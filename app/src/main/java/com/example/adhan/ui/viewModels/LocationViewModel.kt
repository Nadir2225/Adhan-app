package com.example.adhan.ui.viewModels

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    // LiveData to hold the latitude and longitude
    private val _latitude: MutableLiveData<Double> = MutableLiveData()
    val latitude: LiveData<Double> = _latitude

    private val _longitude: MutableLiveData<Double> = MutableLiveData()
    val longitude: LiveData<Double> = _longitude

    // LiveData to report errors in location fetching
    private val locationError: MutableLiveData<String> = MutableLiveData()

    fun getLocation() {
        try {
            fusedLocationClient.lastLocation.addOnCompleteListener { task: Task<Location?> ->
                if (task.isSuccessful && task.result != null) {
                    val location = task.result
                    _latitude.value = location?.latitude
                    _longitude.value = location?.longitude
                } else {
                    locationError.value = "Failed to get location"
                }
            }
        } catch (e: SecurityException) {
            e.message?.let { Log.e("SecurityException", it) }
        }
    }
}
