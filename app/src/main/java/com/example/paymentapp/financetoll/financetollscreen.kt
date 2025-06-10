package com.example.paymentapp.financetoll

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Score
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun ToolCards(title: String, icon: ImageVector, navController: NavController) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { // Ripple effect is automatically added by Material3
                when (title) {
                    "Loan EMI Calculator" -> navController.navigate("loanCalculatorScreen")
                    "Budget Planner" -> navController.navigate("budgetPlannerScreen")
                    "Credit Score Checker" -> navController.navigate("creditScoreCheckerScreen")
                }
            },
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 12.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}




@Composable
fun FinancialToolsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "Financial Tools",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        val tools = listOf(
            "Loan EMI Calculator" to Icons.Default.Calculate,
            "Budget Planner" to Icons.Default.AttachMoney,
            "Credit Score Checker" to Icons.Default.Score
        )

        tools.forEach { (title, icon) ->
            ToolCards(title = title, icon = icon, navController = navController)
        }

    }
}



@Composable
fun LoanCalculatorScreen(navController: NavController) {
    Column {
        Text("Loan EMI Calculator Screen", modifier = Modifier.padding(16.dp))
        // Add UI components for Loan Calculator
    }
}

@Composable
fun BudgetPlannerScreen(navController: NavController) {
    Column {
        Text("Budget Planner Screen", modifier = Modifier.padding(16.dp))
        // Add UI components for Budget Planner, such as a form for entering budget categories, amounts, etc.
    }
}



@Composable
fun CreditScoreCheckerScreen(navController: NavController) {
    Column {
        Text("Credit Score Checker Screen", modifier = Modifier.padding(16.dp))
        // Add UI components for checking the user's credit score, such as a form for entering details or showing results.
    }
}


