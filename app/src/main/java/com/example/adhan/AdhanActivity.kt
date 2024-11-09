package com.example.adhan

import android.app.NotificationManager
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.adhan.ui.screens.AdhanScreen
import com.example.adhan.ui.theme.AdhanTheme

class AdhanActivity : ComponentActivity() {

    private val notificationId = 1
    private lateinit var notificationManager: NotificationManager

    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        mediaPlayer = MediaPlayer.create(this, R.raw.adhan)
        mediaPlayer.setOnCompletionListener {
            finish()
        }

        notificationManager = getSystemService(NotificationManager::class.java)

        mediaPlayer.start()

        setContent {
            AdhanTheme {
                AdhanScreen(onStopClicked = { stopAlarm() })
            }
        }
        cancelNotification()
    }

    private fun stopAlarm() {
        this.onDestroy()
        this.startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }

    private fun cancelNotification() {
        notificationManager.cancel(notificationId)
    }
}