package com.example.paymentapp.actiongrid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import java.text.SimpleDateFormat
import java.util.*

// Updated timestamp type to Firebase Timestamp
data class Transaction(
    val amount: Double = 0.0,
    val receiverId: String = "",
    val senderId: String = "",
    val timestamp: Long = 0L  // <-- Accept Long instead of Timestamp
)




enum class FilterType { ALL, SENT, RECEIVED }

@Composable
fun HistoryScreen(navController: NavController) {
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()
    var transactions by remember { mutableStateOf<List<Transaction>>(emptyList()) }
    var selectedFilter by remember { mutableStateOf(FilterType.ALL) }

    DisposableEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        val listener: ListenerRegistration = db.collection("transactions")
            .addSnapshotListener { snapshot, _ ->
                val all = snapshot?.documents?.mapNotNull {
                    it.toObject(Transaction::class.java)
                } ?: emptyList()
                transactions = all.sortedByDescending { it.timestamp }
            }
        onDispose { listener.remove() }
    }

    val filteredTransactions = when (selectedFilter) {
        FilterType.SENT -> transactions.filter { it.senderId == currentUserId }
        FilterType.RECEIVED -> transactions.filter { it.receiverId == currentUserId }
        FilterType.ALL -> transactions
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ServiceTopBar(title = "Transaction History", navController = navController)

        FilterTabs(selectedFilter) { selectedFilter = it }

        if (filteredTransactions.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No transactions found", fontSize = 18.sp, color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(filteredTransactions) { tx ->
                    TransactionCard(tx, isSent = tx.senderId == currentUserId)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun FilterTabs(selected: FilterType, onSelect: (FilterType) -> Unit) {
    TabRow(selectedTabIndex = selected.ordinal) {
        FilterType.values().forEachIndexed { index, type ->
            Tab(
                selected = selected.ordinal == index,
                onClick = { onSelect(type) },
                text = {
                    Text(type.name.lowercase().replaceFirstChar { it.uppercase() })
                }
            )
        }
    }
}
@Composable
fun TransactionCard(tx: Transaction, isSent: Boolean) {
    val icon = if (isSent) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward
    val amountColor = if (isSent) Color.Red else Color(0xFF2E7D32) // Green
    val title = if (isSent) "Sent to" else "Received from"

    // ✅ Convert Long to Firebase Timestamp, then to Date
    val firebaseTimestamp = remember(tx.timestamp) { Timestamp(tx.timestamp / 1000, 0) }
    val formattedDate = remember(firebaseTimestamp) {
        SimpleDateFormat("MMM dd, yyyy - h:mm a", Locale.getDefault()).format(firebaseTimestamp.toDate())
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = amountColor)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "$title ${tx.receiverId}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(formattedDate, fontSize = 12.sp, color = Color.Gray)
            }
            Text(
                text = "₹${tx.amount}",
                color = amountColor,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
