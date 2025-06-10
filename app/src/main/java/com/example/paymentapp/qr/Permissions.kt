package com.example.paymentapp.qr
import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController


@Composable
fun RequestCameraPermission(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        if (it) onPermissionGranted()
        else Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(android.Manifest.permission.CAMERA)
    }
}

@Composable
fun StartQRScanner(navController: NavController) {
    var permissionGranted by remember { mutableStateOf(false) }

    if (permissionGranted) {
        QRScanScreen(navController)
    } else {
        RequestCameraPermission {
            permissionGranted = true
        }
    }
}
