package com.example.adhan.ui.screens

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.navigation.NavController
import com.example.adhan.models.Screen
import com.example.adhan.ui.viewModels.MainViewModel

@Composable
fun NoPermissionScreen(mainViewModel: MainViewModel, navController: NavController, permission: String) {
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val locationPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            mainViewModel.onPermissionResult(
                permission = permission,
                isGranted = isGranted
            )
        }
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("This app needs access to your location for the feature of Qibla.", textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                locationPermissionResultLauncher.launch(permission)
            }) {
                Text("Request Permission")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("or either if the button didn't work allow permissions from the settings", textAlign = TextAlign.Center)
            Button(onClick = {
                if (checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                    navController.navigate(Screen.App.route) {
                        popUpTo(0)
                    }
                } else {
                    showDialog = true
                }
            }) {
                Text("next")
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
            Text("You have to give us permissions before you can use this app.")
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

