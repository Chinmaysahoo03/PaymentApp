package com.example.paymentapp.qr

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun QRScanScreen(navController: NavController) {
    val context = LocalContext.current
    var scannedData by remember { mutableStateOf<String?>(null) }

    // This Composable triggers the QR scan
    QRCodeScannerScreen { scannedText ->
        // Only set the data if it's not already scanned
        if (scannedData == null) {
            scannedData = scannedText
        }
    }

    // Side effects happen here after QR is scanned
    LaunchedEffect(scannedData) {
        scannedData?.let { data ->
            Log.d("ScannedQR", data)
            Toast.makeText(context, "QR Code: $data", Toast.LENGTH_SHORT).show()

            // Navigate once after scan
            navController.navigate("confirm_payment/${Uri.encode(data)}")
        }
    }
}

