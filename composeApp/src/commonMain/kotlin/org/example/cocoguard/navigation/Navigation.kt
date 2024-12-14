package org.example.cocoguard.navigation

import RegisterPage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavType
import androidx.navigation.navArgument
import org.example.cocoguard.screens.disease.DiseaseTreatmentScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    var loggedInEmail by remember { mutableStateOf<String?>(null) }
    NavHost(navController = navController, startDestination = "onboard") {
        composable("onboard") { OnboardScreen(navController) }
        composable("login") {
            LoginPage(
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToHome = { navController.navigate("home") },
                onEmailLoggedIn = { email ->
                    loggedInEmail = email
                }
            )
        }
        composable("register") {
            RegisterPage(onNavigateToLogin = { navController.navigate("login") },
                onNavigateToHome = { navController.navigate("home") },
                onEmailLoggedIn = { email ->
                    loggedInEmail = email
                }
            )
        }
        composable("home") { HomeScreen(navController, loggedInEmail.toString()) }
        composable("imageUpload/{loggedInEmail}",
            arguments = listOf(navArgument("loggedInEmail") { type = NavType.StringType })
            ) {backStackEntry ->
            val email = backStackEntry.arguments?.getString("loggedInEmail") ?: ""
            ImageUploadScreen(navController = navController, email = email) }
        composable(
            "forecastingQuestion/{loggedInEmail}",
            arguments = listOf(navArgument("loggedInEmail") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("loggedInEmail") ?: ""
            ForecastingQuestionScreen(navController = navController, email = email)
        }
        composable(
            "yieldQuestion/{loggedInEmail}",
            arguments = listOf(navArgument("loggedInEmail") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("loggedInEmail") ?: ""
            YieldQuestionScreen(navController = navController, email = email) }
        composable(
            route = "yieldRecord/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            YieldRecordScreen(navController,userEmail = email) }
        composable(
            route = "forecastingRecord/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ForecastingRecordScreen(navController,userEmail = email)
        }
        composable(route ="diseaseTreatmentScreen/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
            ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            DiseaseTreatmentScreen(navController,userEmail = email)
        }
    }
}
@Composable
fun navControllerWithNavigation(): NavHostController {
    val navController = rememberNavController()
    AppNavigation(navController = navController)
    return navController
}
