package org.example.cocoguard.navigation

import RegisterPage
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.cocoguard.auth.LoginPage
import org.example.cocoguard.screens.HomeScreen
import org.example.cocoguard.screens.OnboardScreen
import org.example.cocoguard.screens.disease.ImageUploadScreen
import org.example.cocoguard.screens.forecasting.ForecastingQuestionScreen
import org.example.cocoguard.screens.forecasting.ForecastingRecordScreen
import org.example.cocoguard.screens.yield.YieldQuestionScreen
import org.example.cocoguard.screens.yield.YieldRecordScreen


@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "onboard") {
        composable("onboard") { OnboardScreen(navController) }

        composable("login") {
            LoginPage(
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToHome = { navController.navigate("home") }
            )
        }

        composable("register") {
            RegisterPage(onNavigateToLogin = { navController.navigate("login") })
        }

        composable("home") { HomeScreen(navController) }
        composable("imageUpload") {ImageUploadScreen() }
        composable("forecastingQuestion") { ForecastingQuestionScreen(navController)}
        composable("yieldQuestion") {YieldQuestionScreen(navController)}
        composable("yieldRecord") { YieldRecordScreen() }
        composable("forecastingRecord") { ForecastingRecordScreen()}

    }
}

@Composable
fun navControllerWithNavigation(): NavHostController {
    val navController = rememberNavController()
    AppNavigation(navController = navController)
    return navController
}