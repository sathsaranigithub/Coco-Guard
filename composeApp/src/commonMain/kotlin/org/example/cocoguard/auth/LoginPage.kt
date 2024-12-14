package org.example.cocoguard.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coco_guard.composeapp.generated.resources.Res
import coco_guard.composeapp.generated.resources.register
import kotlinx.coroutines.launch
import org.example.cocoguard.AuthService
import org.example.cocoguard.FirestoreRepository
import org.example.cocoguard.screens.component.AppFooter
import org.example.cocoguard.screens.component.HeaderText
import org.example.cocoguard.screens.component.InputField
import org.example.cocoguard.screens.component.LoadingAndErrorState
import org.example.cocoguard.screens.component.SideImage
import org.jetbrains.compose.resources.painterResource
@Composable
fun LoginPage(onNavigateToRegister: () -> Unit, onNavigateToHome: () -> Unit, onEmailLoggedIn: (String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val repository = FirestoreRepository()
    val authService = AuthService(repository)
    Row(modifier = Modifier.fillMaxSize().background(Color.White)) {
        SideImage(painter = painterResource(Res.drawable.register), contentDescription = "Coconut background")
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderText(
                title = "Welcome back",
                subtitle = "Welcome to CocoGuard Empowering Coconut Farmers with AI Solutions"
            )
            Card(
                shape = RoundedCornerShape(10.dp), elevation = 4.dp, modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    InputField(value = email, label = "E-mail", onValueChange = { email = it })
                    Spacer(modifier = Modifier.height(10.dp))
                    InputField(value = password, label = "Password", onValueChange = { password = it }, isPassword = true)
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            loading = true
                            errorMessage = null
                            if (email.isNotBlank() && password.isNotBlank()) {
                                coroutineScope.launch {
                                    try {
                                        val isAuthenticated = authService.signIn(email, password)
                                        if (isAuthenticated) {
                                            onEmailLoggedIn(email)
                                            onNavigateToHome()
                                        } else {
                                            errorMessage = "Invalid Email or Password"
                                        }
                                    } catch (e: Exception) {
                                        errorMessage = "Error: ${e.message}"
                                    } finally {
                                        loading = false
                                    }
                                }
                            } else {
                                errorMessage = "Please fill in both fields."
                                loading = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).padding(vertical = 10.dp)
                    ) {
                        Text(text = "Login", color = Color.White, style = TextStyle(fontSize = 18.sp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            LoadingAndErrorState(isLoading = loading, errorMessage = errorMessage)
            AppFooter()
            TextButton(onClick = onNavigateToRegister) {
                Text(
                    text = "Don't have an account? Register",
                    style = TextStyle(color = Color.Black, fontSize = 16.sp, textAlign = TextAlign.Center)
                )
            }
        }
    }
}
