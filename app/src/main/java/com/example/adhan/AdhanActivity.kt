package com.example.adhan

import android.app.NotificationManager
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import com.example.adhan.ui.screens.AdhanScreen
import com.example.adhan.ui.theme.AdhanTheme
import com.example.adhan.ui.viewModels.AdhanViewModel

class AdhanActivity : ComponentActivity() {

    private val notificationId = 1
    private lateinit var notificationManager: NotificationManager
    private var mediaPlayer: MediaPlayer? = null

//    private val adhanViewModel: AdhanViewModel by viewModels()
    private val adhanViewModel: AdhanViewModel
        get() = (application as MyApplication).adhanViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        notificationManager = getSystemService(NotificationManager::class.java)

        // Observe adhan LiveData to respond to changes
        adhanViewModel.adhan.observe(this, Observer { adhan ->
            mediaPlayer?.release() // Release any existing player to avoid overlap
            mediaPlayer = MediaPlayer.create(this, adhan).apply {
                setOnCompletionListener {
                    finish() // Close the activity when adhan finishes
                }
                start() // Start playing the selected adhan
            }
        })

        setContent {
            AdhanTheme {
                AdhanScreen(onStopClicked = { stopAlarm() })
            }
        }
        cancelNotification()
    }

    private fun stopAlarm() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        finish() // Close AdhanActivity
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun cancelNotification() {
        notificationManager.cancel(notificationId)
    }
}
