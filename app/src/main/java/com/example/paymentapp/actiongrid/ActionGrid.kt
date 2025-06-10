package com.example.paymentapp.actiongrid
// Compose UI
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Animation
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring

// Navigation
import androidx.navigation.NavController

// Coroutine delay
import kotlinx.coroutines.delay

// For Tooltip (optional - if you’re using Compose Material Tooltips)
import androidx.compose.ui.window.Popup
// OR custom tooltips if you're implementing them yourself

// Resource annotation (used in ActionIcon.Resource)
import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import com.example.paymentapp.R




sealed class ActionIcon {
    data class Vector(val icon: ImageVector) : ActionIcon()
    data class Resource(@DrawableRes val resId: Int) : ActionIcon()
}
@Composable
fun ActionItem(
    label: String,
    icon: ActionIcon,
    onClick: () -> Unit
) {
    var clicked by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (clicked) 1.1f else 1f,
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
            onClick()
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .graphicsLayer(scaleX = scale, scaleY = scale)
                .size(60.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color(0xFF80DEEA), Color(0xFF006064)),
                        radius = 200f
                    ),
                    shape = CircleShape
                )
                .clickable { clicked = true },
            contentAlignment = Alignment.Center
        ) {
            when (icon) {
                is ActionIcon.Vector -> Icon(
                    imageVector = icon.icon,
                    contentDescription = label,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
                is ActionIcon.Resource -> Image(
                    painter = painterResource(id = icon.resId),
                    contentDescription = label,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))



        Text(label, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}



@Composable
fun CustomTooltip(label: String, modifier: Modifier = Modifier) {
    var isVisible by remember { mutableStateOf(true) }

    if (isVisible) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = { isVisible = false }
        ) {
            Surface(
                color = Color.Black,
                shape = RoundedCornerShape(6.dp),
                modifier = modifier
            ) {
                Text(
                    text = label,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    fontSize = 12.sp
                )
            }
        }
    }
}



@Composable
fun ActionGrid(navController: NavController) {
    val actions = listOf(
        "Send Money" to ActionIcon.Vector(Icons.Default.Send),
        "Scan QR" to ActionIcon.Vector(Icons.Default.QrCode),
        "Pay Bills" to ActionIcon.Resource(R.drawable.bill),
        "Mobile Recharge" to ActionIcon.Vector(Icons.Default.PhoneAndroid),
        "Wallet" to ActionIcon.Vector(Icons.Default.AccountBalanceWallet),
        "History" to ActionIcon.Vector(Icons.Default.History),
        "Nearby" to ActionIcon.Vector(Icons.Default.Place),
        "More" to ActionIcon.Vector(Icons.Default.MoreHoriz)
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Services", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        actions.chunked(4).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowItems.forEach { (label, icon) ->
                    ActionItem(label, icon) {
                        when (label) {
                            "Scan QR" -> navController.navigate("qr_scan")
                            "Send Money" -> navController.navigate("send_money")
                            "Pay Bills" -> navController.navigate("bills")
                            "Mobile Recharge" -> navController.navigate("recharge")
                            "Wallet" -> navController.navigate("wallet")
                            "History" -> navController.navigate("history")
                            "Nearby" -> navController.navigate("nearby")
                            "More" -> navController.navigate("more")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
