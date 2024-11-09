package com.example.adhan.ui.screens

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.navigation.NavController
import com.example.adhan.models.Screen
import com.example.adhan.ui.theme.dark
import com.example.adhan.ui.viewModels.MainViewModel

@Composable
fun NoPermissionScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    permissions: Map<String, String>, // The permissions map
    checkPermissions: () -> Boolean // Function to check if permissions are granted
) {
    var showDialog by remember { mutableStateOf(false) }
    var locationPermissionGranted by remember { mutableStateOf(false) }
    var notificationPermissionGranted by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val notificationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            notificationPermissionGranted = isGranted
            mainViewModel.onPermissionResult(
                permission = "notifications",
                isGranted = isGranted
            )
        }
    )
    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            locationPermissionGranted = isGranted
            if (isGranted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    notificationPermissionResultLauncher.launch(permissions["notifications"] ?: "")
                }
            }
        }
    )


    Box(
        modifier = Modifier.fillMaxSize().background(dark),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "This app needs your permission to display notifications and location to calculate the prayer times.",
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Single button for both permissions
            Button(
                onClick = {
                    // First request location permission
                    locationPermissionResultLauncher.launch(permissions["location"] ?: "")
                },
                colors = ButtonColors(
                    containerColor = Color(0xFFCE8920),
                    contentColor = Color.White,
                    disabledContentColor = Color.White,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text(text = "Request Location and Notifications Permission", color = Color.White, textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "If the button doesn't work, allow permissions from the settings.",
                textAlign = TextAlign.Center,
                color = Color.White
            )
            Button(
                onClick = {
                    if (checkPermissions()) {
                        navController.navigate(Screen.App.route) {
                            popUpTo(0)
                        }
                    } else {
                        showDialog = true
                    }
                },
                colors = ButtonColors(
                    containerColor = Color(0xFFCE8920),
                    contentColor = Color.White,
                    disabledContentColor = Color.White,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text(text = "Next", color = Color.White)
            }
        }

        if (showDialog) {
            SimpleOkDialog(onDismiss = { showDialog = false })
        }
    }
}

@Composable
fun SimpleOkDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(text = "Permissions Request")
        },
        text = {
            Text("You have to give us your permissions in order to use this app.")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        }
    )
}
