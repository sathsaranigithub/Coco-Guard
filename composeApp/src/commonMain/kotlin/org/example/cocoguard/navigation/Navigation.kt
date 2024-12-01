package org.example.cocoguard.navigation


import RegisterPage
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.cocoguard.AuthService
import org.example.cocoguard.FirestoreRepository
import org.example.cocoguard.User
import org.example.cocoguard.auth.LoginPage
import org.example.cocoguard.screens.HomeScreen
import org.example.cocoguard.screens.OnboardScreen
import org.example.cocoguard.screens.disease.ImageUploadScreen
import org.example.cocoguard.screens.forecasting.ForecastingQuestionScreen
import org.example.cocoguard.screens.forecasting.ForecastingRecordScreen
import org.example.cocoguard.screens.yield.YieldQuestionScreen
import org.example.cocoguard.screens.yield.YieldRecordScreen
import java.security.MessageDigest
import androidx.lifecycle.lifecycleScope


@Composable
fun AppNavigation(navController: NavHostController) {

    val repository = FirestoreRepository()
        val authService = AuthService(repository)
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        var isLoading by remember { mutableStateOf(false) }
        var message by remember { mutableStateOf("") }


    NavHost(navController = navController, startDestination = "onboard") {
        composable("onboard") { OnboardScreen(navController) }

        composable("login") {
            LoginPage(
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToHome = { navController.navigate("home") }
            )
        }

        composable("register") {
            RegisterPage(onNavigateToLogin = { navController.navigate("login") },
                onSave = { email, password ->
                    if (email.isNotBlank() && password.isNotBlank()) {
                        coroutineScope.launch {
                            try {
                                isLoading = true // Show loading
                                message = ""
                                val encryptedPassword = encryptPassword(password)
                                val result = repository.addUser(User(email, encryptedPassword))
                                withContext(Dispatchers.Main) {
                                    isLoading = false // Hide loading
                                    if (result.isSuccess) {
                                        message = "User added successfully!" // Success message
                                    } else {
                                        message = "Error: ${result.exceptionOrNull()?.message}" // Error message
                                    }
                                }
                            } catch (e: Exception) {
                                withContext(Dispatchers.Main) {
                                    isLoading = false // Hide loading
                                    message = "Error: ${e.message}" // Error message
                                }
                            }
                        }
                    } else {
                        message = "Please fill in both fields." // Validation message
                    }
                },
                onSaveUser = { email, password ->

                    coroutineScope.launch {
                        try {
                            isLoading = true
                            message = ""
                            val encryptedPassword = encryptPassword(password)

                            val result = repository.addUser(User(email, encryptedPassword))
                            if (result.isSuccess) {
                                message = "User added successfully!" // Success message
                            } else {
                                message = "Error: ${result.exceptionOrNull()?.message}" // Error message
                            }
                        } catch (e: Exception) {
                            message = "Error: ${e.message}" // Error message
                        } finally {
                            isLoading = false
                        }
                    }
                }
            )
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
fun encryptPassword(password: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashedBytes = messageDigest.digest(password.toByteArray())
    return hashedBytes.joinToString("") { "%02x".format(it) }
}