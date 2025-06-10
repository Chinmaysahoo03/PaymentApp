package com.example.paymentapp.actiongrid

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@Composable
fun SendMoneyScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val senderUid = auth.currentUser?.uid.orEmpty()

    var amountText by remember { mutableStateOf("") }
    var receiverInput by remember { mutableStateOf("") }
    var receiverName by remember { mutableStateOf("") }
    var senderBalance by remember { mutableStateOf(0.0) }
    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val shakeOffset = remember { Animatable(0f) }

    // Fetch sender wallet balance
    LaunchedEffect(senderUid) {
        db.collection("user").document(senderUid).get()
            .addOnSuccessListener { document ->
                val balance = document.get("walletBalance")
                senderBalance = (balance as? Number)?.toDouble() ?: 0.0
            }
    }

    // Lookup receiver name when UPI/phone changes
    LaunchedEffect(receiverInput) {
        if (receiverInput.length >= 5) {
            db.collection("user")
                .whereEqualTo("upiId", receiverInput)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (!snapshot.isEmpty) {
                        val name = snapshot.documents[0].getString("name") ?: ""
                        receiverName = name
                    } else {
                        db.collection("user")
                            .whereEqualTo("phone", receiverInput)
                            .get()
                            .addOnSuccessListener { phoneSnap ->
                                receiverName = if (!phoneSnap.isEmpty)
                                    phoneSnap.documents[0].getString("name") ?: ""
                                else ""
                            }
                    }
                }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .graphicsLayer { translationX = shakeOffset.value } // Apply shake if needed
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Send Money", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = receiverInput,
                onValueChange = { receiverInput = it },
                label = { Text("Receiver UPI ID or Phone") },
                modifier = Modifier.fillMaxWidth()
            )
            if (receiverName.isNotEmpty()) {
                Text("Receiver: $receiverName", color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val amount = amountText.toDoubleOrNull()
                    if (amount == null || amount <= 0) {
                        coroutineScope.launch {
                            shakeOffset.animateTo(
                                targetValue = 30f,
                                animationSpec = tween(durationMillis = 50, easing = LinearEasing)
                            )
                            shakeOffset.animateTo(0f, tween(100))
                        }
                        Toast.makeText(context, "Enter valid amount", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (receiverInput.isBlank()) {
                        coroutineScope.launch {
                            shakeOffset.snapTo(25f)
                            shakeOffset.animateTo(0f, tween(100))
                        }
                        Toast.makeText(context, "Enter valid UPI or phone", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    if (amount > senderBalance) {
                        coroutineScope.launch {
                            shakeOffset.snapTo(-25f)
                            shakeOffset.animateTo(0f, tween(100))
                        }
                        Toast.makeText(context, "Insufficient balance", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    // Look for receiver
                    db.collection("user")
                        .whereEqualTo("upiId", receiverInput)
                        .get()
                        .addOnSuccessListener { result ->
                            val receiverDoc = if (!result.isEmpty) result.documents[0] else null

                            if (receiverDoc == null) {
                                // Try phone
                                db.collection("user")
                                    .whereEqualTo("phone", receiverInput)
                                    .get()
                                    .addOnSuccessListener { phoneResult ->
                                        val phoneDoc = phoneResult.documents.firstOrNull()
                                        if (phoneDoc == null) {
                                            Toast.makeText(context, "Receiver not found", Toast.LENGTH_SHORT).show()
                                            return@addOnSuccessListener
                                        }
                                        completeTransaction(db, senderUid, phoneDoc.id, amount, senderBalance) {
                                            senderBalance -= amount
                                            showSuccess = true
                                            coroutineScope.launch {
                                                kotlinx.coroutines.delay(2000)
                                                showSuccess = false
                                            }
                                        }
                                    }
                            } else {
                                completeTransaction(db, senderUid, receiverDoc.id, amount, senderBalance) {
                                    senderBalance -= amount
                                    showSuccess = true
                                    coroutineScope.launch {
                                        kotlinx.coroutines.delay(2000)
                                        showSuccess = false
                                    }
                                }
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Failed to find receiver", Toast.LENGTH_SHORT).show()
                        }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Send")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Current Balance: ₹$senderBalance")
        }

        // ✅ Success Box Animation
        AnimatedVisibility(
            visible = showSuccess,
            enter = slideInVertically(initialOffsetY = { -100 }) + fadeIn(),
            exit = fadeOut()
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
                    .background(Color(0xFF4CAF50), RoundedCornerShape(12.dp))
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Money sent successfully!", color = Color.White)
            }
        }
    }
}

// 🔄 Transaction logic moved here
fun completeTransaction(
    db: FirebaseFirestore,
    senderUid: String,
    receiverId: String,
    amount: Double,
    senderBalance: Double,
    onSuccess: () -> Unit
) {
    val senderRef = db.collection("user").document(senderUid)
    val receiverRef = db.collection("user").document(receiverId)

    db.runBatch { batch ->
        batch.update(senderRef, "walletBalance", senderBalance - amount)
        batch.update(receiverRef, "walletBalance", FieldValue.increment(amount))
    }.addOnSuccessListener {
        // Save transaction history
        val receiverRef = db.collection("user").document(receiverId)
        receiverRef.get().addOnSuccessListener { receiverDoc ->
            val receiverUpi = receiverDoc.getString("upiId") ?: ""

            val transactionData = hashMapOf(
                "senderId" to senderUid,
                "receiverId" to receiverId,
                "receiverUpiId" to receiverUpi,
                "amount" to amount,
                "timestamp" to System.currentTimeMillis()  // Store as Long (epoch millis)
            )

            db.collection("transactions").add(transactionData)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { onSuccess() } // still proceed on failure
        }
    }
}


