package org.example.cocoguard.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons


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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coco_guard.composeapp.generated.resources.Res
import coco_guard.composeapp.generated.resources.logo
import coco_guard.composeapp.generated.resources.register
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.cocoguard.AuthService
import org.example.cocoguard.FirestoreRepository

import org.example.cocoguard.ui.theme.lemonadaFontFamily
import org.example.cocoguard.ui.theme.workSansBoldFontFamily
import org.example.cocoguard.ui.theme.workSansFontFamily
import org.example.cocoguard.ui.theme.workSansSemiBoldFontFamily
//import org.example.cocoguard.utils.AuthUtil
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.FontResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginPage(
    onNavigateToRegister: () -> Unit,
              onNavigateToHome: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) } // Loading state
    var errorMessage by remember { mutableStateOf<String?>(null) } // Error state
    val coroutineScope = rememberCoroutineScope()
    val repository = FirestoreRepository()
    val authService = AuthService(repository)



    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Set the background to white
    ) {
        // Image aligned to the left, matching parent height
        Image(
            painter = painterResource(Res.drawable.register),
            contentDescription = "Coconut background",
            modifier = Modifier
                .fillMaxHeight() // Match the parent's height
                .fillMaxWidth(0.3f), // Set the width to 30% of the screen
            contentScale = ContentScale.Crop
        )

        // Content aligned to the right
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp)) // Add padding from top

            // Welcome Text
            Text(
                text = "Welcome back",
                style = TextStyle(
                    fontFamily = workSansBoldFontFamily(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 40.dp).align(Alignment.CenterHorizontally)
            )

            // Subheading Text
            Text(
                text = "Welcome to CocoGuard: Empowering Coconut Farmers with AI Solutions",
                style = TextStyle(
                    fontFamily = workSansSemiBoldFontFamily(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // Card for Email, Password, and Login Button
            Card(
                shape = RoundedCornerShape(10.dp),
                elevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp) // Padding inside the card
                ) {
                    // Email TextField
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("E-mail") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            textColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // Password TextField with toggle icon
                    var passwordVisible by remember { mutableStateOf(false) }
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            textColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    // Login Button
                    Button(
                        onClick = {
                            loading = true 
                            errorMessage = null

                            if (email.isNotBlank() && password.isNotBlank()) {
                                coroutineScope.launch {
                                    try {
                                        // Simulated authentication service
                                        val isAuthenticated = authService.signIn(email, password)
                                        if (isAuthenticated) {
                                            onNavigateToHome() // Navigate to Home page
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .padding(vertical = 10.dp)
                    ) {
                        Text(
                            text = "Login",
                            color = Color.White,
                            style = TextStyle(fontSize = 18.sp)
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.height(30.dp)) // Space between button and logo

            // Logo Image
            Image(
                painter = painterResource(Res.drawable.logo), // Replace with your logo resource
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(100.dp) // Set the size for the logo
                    .padding(bottom = 8.dp)
            )

            // App Name Text
            Text(
                text = "Coco Guard",
                style = TextStyle(
                    fontFamily = lemonadaFontFamily(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            )
            // Register Link
            TextButton(
                onClick = onNavigateToRegister, // Navigate to RegisterPage
                modifier = Modifier.padding(bottom = 5.dp)
            ) {
                Text(
                    text = "Don't have an account? Register",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = workSansFontFamily(),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(top = 30.dp)
                )
            }
        }
    }
}
