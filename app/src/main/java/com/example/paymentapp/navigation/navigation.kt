package com.example.paymentapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.paymentapp.actiongrid.BalanceScreen
import com.example.paymentapp.actiongrid.HistoryScreen
import com.example.paymentapp.actiongrid.SendMoneyScreen
import com.example.paymentapp.billblock.BillDetailsScreen
import com.example.paymentapp.financetoll.BudgetPlannerScreen
import com.example.paymentapp.financetoll.CreditScoreCheckerScreen
import com.example.paymentapp.financetoll.LoanCalculatorScreen
import com.example.paymentapp.homescreen.HomeScreen
import com.example.paymentapp.loginscreen.PhoneInputScreen
import com.example.paymentapp.otpvarification.OtpVerificationScreen
import com.example.paymentapp.qr.ConfirmPaymentScreen
import com.example.paymentapp.qr.StartQRScanner
import com.example.paymentapp.servicescreens.BillsScreen
import com.example.paymentapp.servicescreens.MoreScreen
import com.example.paymentapp.servicescreens.NearbyScreen
import com.example.paymentapp.servicescreens.RechargeScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object OtpVerification : Screen("otp_verification")
    object Home : Screen("home")
}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            PhoneInputScreen(
                onOtpSent = { verificationId, phoneNumber ->
                    navController.navigate(Screen.OtpVerification.route + "/$verificationId/$phoneNumber")
                }
            )
        }

        composable(Screen.OtpVerification.route + "/{verificationId}/{phoneNumber}") { backStackEntry ->
            val verificationId = backStackEntry.arguments?.getString("verificationId") ?: ""
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
            OtpVerificationScreen(
                verificationId = verificationId,
                phoneNumber = phoneNumber,
                onVerificationSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable("qr_scan") {
            StartQRScanner(navController)
        }
        composable(
            "confirm_payment/{qrData}",
            arguments = listOf(navArgument("qrData") { type = NavType.StringType })
        ) {
            val qrData = it.arguments?.getString("qrData") ?: ""
            ConfirmPaymentScreen(navController, qrData)
        }
        composable("bill/{type}") { backStackEntry ->
            val billType = backStackEntry.arguments?.getString("type") ?: "Unknown"
            BillDetailsScreen(billType)
        }
        composable("send_money") { SendMoneyScreen(navController) }
        composable("bills") { BillsScreen(navController) }
        composable("recharge") { RechargeScreen(navController) }
        composable("wallet") { BalanceScreen(navController) }
        composable("history") { HistoryScreen(navController) }
        composable("nearby") { NearbyScreen(navController) }
        composable("more") { MoreScreen(navController) }
        composable("homeScreen") {
            HomeScreen(navController)
        }
        composable("loanCalculatorScreen") {
            LoanCalculatorScreen(navController)
        }
        composable("budgetPlannerScreen") {
            BudgetPlannerScreen(navController)
        }
        composable("creditScoreCheckerScreen") {
            CreditScoreCheckerScreen(navController)
        }




    }
}
