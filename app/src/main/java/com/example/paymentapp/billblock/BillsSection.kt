package com.example.paymentapp.billblock


import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.paymentapp.R  // Import your R file correctly

@Composable
fun BillsSection(navToBill: (String) -> Unit) {
    val billItems = listOf(
        "Electricity" to R.drawable.baseline_bolt_24,
        "Water" to R.drawable.baseline_water_drop_24,
        "Gas" to R.drawable.baseline_local_fire_department_24,
        "Broadband" to R.drawable.baseline_wifi_24,
        "Mobile" to R.drawable.baseline_smartphone_24,
        "DTH" to R.drawable.baseline_tv_24,
        "Insurance" to R.drawable.baseline_health_and_safety_24
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Bills & Recharges",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            billItems.forEach { (label, imageRes) ->
                var clicked by remember { mutableStateOf(false) }

                val scale by animateFloatAsState(
                    targetValue = if (clicked) 1.15f else 1f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    label = "scale"
                )

                LaunchedEffect(clicked) {
                    if (clicked) {
                        delay(150)
                        clicked = false
                        navToBill(label)
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clickable { clicked = true }
                ) {
                    Box(
                        modifier = Modifier
                            .graphicsLayer(scaleX = scale, scaleY = scale)
                            .size(60.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(Color(0xFF42A5F5), Color(0xFF095F86)),
                                    radius = 200f
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = label,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        label,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun BillDetailsScreen(billType: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to $billType Bill Payment!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}
