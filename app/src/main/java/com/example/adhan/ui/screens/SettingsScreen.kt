package com.example.adhan.ui.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.adhan.AdhanActivity
import com.example.adhan.R
import com.example.adhan.ui.theme.dark
import com.example.adhan.ui.viewModels.AdhanViewModel

@Composable
fun SettingsScreen(adhanViewModel: AdhanViewModel) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(dark),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "choose your adhan", color = Color.White)
        Spacer(modifier = Modifier.height(10.dp))
        SimpleDropdown(adhanViewModel = adhanViewModel)
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                val intent = Intent(context, AdhanActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                context.startActivity(intent)
            },
            colors = ButtonColors(
                containerColor = Color(0xFFCE8920),
                contentColor = Color.White,
                disabledContainerColor = Color(0xFFCE8920),
                disabledContentColor = Color.White
            )
        ) {
            Text(text = "see Demo")
        }
    }
}

@Composable
fun SimpleDropdown(adhanViewModel: AdhanViewModel) {
    val adhan = adhanViewModel.adhan.observeAsState()

    // State to handle if the dropdown is visible or not
    var expanded by remember { mutableStateOf(false) }


    // List of options with display text and corresponding values
    val options = mapOf(
        R.raw.a1 to "Adhan 1",
        R.raw.a2 to "Adhan 2",
        R.raw.a3 to "Adhan 3",
        R.raw.a4 to "Adhan 4",
        R.raw.a5 to "Adhan 5",
        R.raw.a6 to "Adhan 6",
        R.raw.a7 to "Adhan 7",
        R.raw.a8 to "Adhan 8",
        R.raw.a9 to "Adhan 9",
        R.raw.a10 to "Adhan 10",
    )

    // State to track the selected item (text and value)
    var selectedOption by remember { mutableStateOf(options[adhan.value]) }
//    var selectedOption by remember { mutableStateOf(options[adhan.value!!].second) }

    // Button to open the dropdown
    Box(contentAlignment = Alignment.Center) {
        // Text displaying the selected option
        TextButton(onClick = { expanded = !expanded }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                selectedOption?.let { Text(text = it) }
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown arrow"
                )
            }
        }

        // DropdownMenu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { (value, text) ->
                DropdownMenuItem(
                    text = { Text(text = text) },
                    onClick = {
                        selectedOption = text // Set the selected option's text and value
                        adhanViewModel.updateAdhan(value)
                        expanded = false // Close the dropdown after selection
                    }
                )
            }
        }
    }
}