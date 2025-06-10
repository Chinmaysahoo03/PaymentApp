package com.example.paymentapp.otpvarification

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

@Composable
fun OtpVerificationScreen(
    verificationId: String,
    phoneNumber: String,
    onVerificationSuccess: () -> Unit
) {
    var otp by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Enter OTP sent to $phoneNumber",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = otp,
            onValueChange = { otp = it },
            label = { Text("OTP") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (otp.length < 6) {
                    error = "Please enter a valid 6-digit OTP"
                    return@Button
                }

                val credential = PhoneAuthProvider.getCredential(verificationId, otp)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onVerificationSuccess()
                        } else {
                            error = task.exception?.localizedMessage
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Verify")
        }

        error?.let {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}