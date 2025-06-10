package com.example.paymentapp.qr

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController




@Composable
fun ConfirmPaymentScreen(navController: NavController, qrData: String) {
    val context = LocalContext.current  // This provides the context for Toast

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Pay to:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(qrData, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            Toast.makeText(context, "Payment Sent to $qrData", Toast.LENGTH_SHORT).show()  // Toast in onClick is valid here
            navController.popBackStack("home", inclusive = false)
        }) {
            Text("Confirm Payment")
        }
    }
}
