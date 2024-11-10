package com.example.adhan

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.adhan.ui.viewModels.AdhanViewModel

class MyApplication : Application() {
    val adhanViewModel: AdhanViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(this)
            .create(AdhanViewModel::class.java)
    }
}