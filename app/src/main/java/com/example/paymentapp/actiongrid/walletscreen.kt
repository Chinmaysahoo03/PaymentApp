package com.example.paymentapp.actiongrid


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.paymentapp.firebasedata.FirestoreHelper

import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceTopBar(title: String, navController: NavController) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun BalanceScreen(navController: NavController) {
    val context = LocalContext.current
    val walletBalance = remember { mutableStateOf<Float?>(null) }
    val userName = remember { mutableStateOf("") }
    val upiId = remember { mutableStateOf("") }

    val phoneNumber = FirebaseAuth.getInstance().currentUser?.phoneNumber

    LaunchedEffect(phoneNumber) {
        phoneNumber?.let {
            FirestoreHelper.db.collection("user")
                .whereEqualTo("phone", it)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        walletBalance.value = document.getDouble("walletBalance")?.toFloat()
                        userName.value = document.getString("name") ?: ""
                        upiId.value = document.getString("upiId") ?: ""
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error fetching wallet data", Toast.LENGTH_SHORT).show()
                }
        }
    }

    Column {
        ServiceTopBar(title = "Wallet", navController = navController)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            if (walletBalance.value == null) {
                CircularProgressIndicator()
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Hello, ${userName.value}", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("UPI: ${upiId.value}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Wallet Balance: ₹${walletBalance.value}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    }
}
