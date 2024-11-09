package com.example.adhan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.adhan.R

@Composable
fun AdhanScreen(onStopClicked: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(painter = painterResource(R.drawable.adhan_bg), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillHeight)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("It's time for prayer!", color = Color.White, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onStopClicked,
                colors = ButtonColors(
                    containerColor = Color(0xFFCE8920),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFCE8920),
                    disabledContentColor = Color.White
                )
            ) {
                Text("Stop Adhan")
            }
        }
    }
}
