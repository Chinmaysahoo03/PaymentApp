package com.example.paymentapp.homescreen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.paymentapp.businesssection.BusinessesSection
import com.example.paymentapp.actiongrid.ActionGrid
import com.example.paymentapp.billblock.BillsSection
import com.example.paymentapp.financetoll.FinancialToolsScreen


@Composable
fun HomeScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        TopBar()
        Spacer(modifier = Modifier.height(16.dp))
        PromoCarousel()
        Spacer(modifier = Modifier.height(16.dp))
        ActionGrid(navController)
        Spacer(modifier = Modifier.height(16.dp))
        PeopleCarousel()
        Spacer(modifier = Modifier.height(16.dp))
        BillsSection(navToBill = { billType ->
            navController.navigate("bill/${billType}")
        })

        Spacer(modifier = Modifier.height(16.dp))
        BusinessesSection()
        Spacer(modifier = Modifier.height(16.dp))
        RewardsSection()
        Spacer(modifier = Modifier.height(16.dp))
        ManageFinancesSection()
        Spacer(modifier = Modifier.height(16.dp))
        FinancialToolsScreen(navController)
        Spacer(modifier = Modifier.height(32.dp))
        Footer()
    }
}


@Composable
fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Search for people, bills...") },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(20.dp)
        )
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profile",
            modifier = Modifier
                .size(50.dp)
                .padding(start = 8.dp)
                .clickable {
                    println("Clicked on Profile")
                }
        )
    }
}

@Composable
fun PromoCarousel() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PromoCard("Earn Rewards", "Invite your friends and earn")
        PromoCard("Cashback", "Pay bills and get cashback")
    }
}

@Composable
fun PromoCard(title: String, description: String) {
    ElevatedCard(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(220.dp)
            .height(120.dp)
            .clickable {
                println("Clicked on $title")
            },
        colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFE0F7FA))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = description)
        }
    }
}




@Composable
fun PeopleCarousel() {
    Column {
        Text("People", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            repeat(10) { index ->
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(end = 12.dp)) {
                    Surface(
                        shape = CircleShape,
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                println("Clicked on Person $index")
                            },
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        // Placeholder for image
                    }
                    Text("Person $index", fontSize = 12.sp)
                }
            }
        }
    }
}






@Composable
fun RewardsSection() {
    Column {
        Text("Rewards & Referrals", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        ElevatedCard(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    println("Clicked on Invite & Earn")
                },
            colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFFFF3E0))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Invite & Earn")
                Text("Get ₹100 cashback for every friend")
            }
        }
    }
}

@Composable
fun ManageFinancesSection() {
    Column {
        Text("Manage Finances", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        repeat(2) { index ->
            ToolCard(title = "Finance Tool $index")
        }
    }
}


@Composable
fun ToolCard(title: String) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                println("Clicked on $title")
            }
    ) {
        Box(modifier = Modifier.padding(12.dp), contentAlignment = Alignment.CenterStart) {
            Text(title)
        }
    }
}

@Composable
fun Footer() {
    Text("SwiftPay © 2025")
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()

    HomeScreen(navController)
}


