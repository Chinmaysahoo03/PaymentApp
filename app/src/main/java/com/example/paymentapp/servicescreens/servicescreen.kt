package com.example.paymentapp.servicescreens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.TopAppBarDefaults



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
fun BillsScreen(navController: NavController) {
    Column {
        ServiceTopBar(title = "Pay Bills", navController = navController)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Bills Screen", fontSize = 20.sp)
        }
    }
}


@Composable
fun RechargeScreen(navController: NavController) {
    Column {
        ServiceTopBar(title = "Mobile Recharge", navController = navController)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Recharge Screen", fontSize = 20.sp)
        }
    }
}







@Composable
fun NearbyScreen(navController: NavController) {
    Column {
        ServiceTopBar(title = "Nearby", navController = navController)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Nearby Screen", fontSize = 20.sp)
        }
    }
}


@Composable
fun MoreScreen(navController: NavController) {
    Column {
        ServiceTopBar(title = "More Services", navController = navController)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "More Screen", fontSize = 20.sp)
        }
    }
}


@Composable
fun ServicePlaceholderScreen(title: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$title Screen",
            fontSize = 20.sp
        )
    }
}
