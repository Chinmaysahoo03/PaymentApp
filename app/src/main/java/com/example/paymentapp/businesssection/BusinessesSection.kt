package com.example.paymentapp.businesssection

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paymentapp.R

data class BusinessInfo(
    val name: String,
    val description: String,
    val logoRes: Int,
    val background: Brush,
    val url: String
)

@Composable
fun BusinessesSection() {
    val businesses = listOf(
        BusinessInfo("IRCTC", "Train Tickets", R.drawable.irctc, Brush.linearGradient(listOf(Color(0xFF2196F3), Color(0xFF64B5F6))), "https://www.irctc.co.in"),
        BusinessInfo("RedBus", "Bus Bookings", R.drawable.redbus, Brush.linearGradient(listOf(Color(0xFFD32F2F), Color(0xFFEF5350))), "https://www.redbus.in"),
        BusinessInfo("MakeMyTrip", "Flights & Hotels", R.drawable.makemy, Brush.linearGradient(listOf(Color(0xFF388E3C), Color(0xFF81C784))), "https://www.makemytrip.com"),
        BusinessInfo("Slice", "Credit & Loans", R.drawable.slice, Brush.linearGradient(listOf(Color(0xFF8E24AA), Color(0xFFBA68C8))), "https://www.sliceit.com")
    )

    Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp)) {
        Text("Businesses", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            businesses.forEach { business ->
                BusinessCard(business = business)
            }
        }
    }
}

@Composable
fun BusinessCard(business: BusinessInfo) {
    val context = LocalContext.current
    var flipped by remember { mutableStateOf(false) }

    val animatedRotationY by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        label = "rotation"
    )

    val alphaFront = if (animatedRotationY <= 90f) 1f else 0f
    val alphaBack = if (animatedRotationY > 90f) 1f else 0f

    Card(
        modifier = Modifier
            .padding(end = 12.dp)
            .size(width = 160.dp, height = 110.dp)
            .clickable {
                flipped = !flipped
                if (!flipped) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(business.url))
                    context.startActivity(intent)
                }
            }
            .graphicsLayer {
                rotationY = animatedRotationY
                cameraDistance = 12 * density
                shadowElevation = if (animatedRotationY <= 90f) 8.dp.toPx() else 0f // Adding dynamic shadow
            },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier
                .background(business.background)
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            // Front side (with shadow and depth)
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(alphaFront)
            ) {
                Image(
                    painter = painterResource(id = business.logoRes),
                    contentDescription = business.name,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = business.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Text(
                        text = business.description,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            // Back side (with blur effect and shadow)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(alphaBack)
                    .graphicsLayer {
                        shadowElevation = 8.dp.toPx() // Add shadow behind the back side
                    }
                    // Applying blur to the back side for more realistic look
            ) {
                Text(
                    text = "Book",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}
