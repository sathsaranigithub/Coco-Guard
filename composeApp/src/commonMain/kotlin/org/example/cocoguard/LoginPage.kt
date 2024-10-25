package org.example.cocoguard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cocoguard.composeapp.generated.resources.Res
import cocoguard.composeapp.generated.resources.lemonada
import cocoguard.composeapp.generated.resources.login
import cocoguard.composeapp.generated.resources.logo
import cocoguard.composeapp.generated.resources.worksansbold
import cocoguard.composeapp.generated.resources.worksansregular
import cocoguard.composeapp.generated.resources.worksanssemibold
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.FontResource
import org.jetbrains.compose.resources.painterResource

// Define the Work Sans font family
@OptIn(ExperimentalResourceApi::class)
@Composable
fun workSansBoldFontFamily(): FontFamily {
    return FontFamily(
        org.jetbrains.compose.resources.Font(Res.font.worksansbold, FontWeight.Bold))
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun workSansSemiBoldFontFamily(): FontFamily {
    return FontFamily(
        org.jetbrains.compose.resources.Font(Res.font.worksanssemibold, FontWeight.Bold))
}

// Define Custom TextStyle for the Heading
@Composable
fun loginHeadTextStyle(): TextStyle {
    return TextStyle(
        fontFamily = workSansBoldFontFamily(),
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black // Heading color
    )
}

// Define Custom TextStyle for the Subheading
@Composable
fun subHeadTextStyle(): TextStyle {
    return TextStyle(
        fontFamily = workSansSemiBoldFontFamily(),
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal,
        color = Color.Black // Subheading color
    )
}

// Login page Composable
@Composable
fun LoginPage() {
    // Background setup with an image
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Set the background to white
    ) {
        // Image aligned to the left, matching parent height
        Image(
            painter = painterResource(Res.drawable.login), // Replace with your image resource
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
            Spacer(modifier = Modifier.height(20.dp)) // Add padding from top

            // Welcome Text
            Text(
                text = "Welcome back",
                style = TextStyle(
                    fontFamily = workSansBoldFontFamily(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 40.dp) .align(Alignment.CenterHorizontally)
            )

            // Subheading Text
            Text(
                text = "\"Welcome to CocoGuard: Empowering Coconut Farmers with AI Solutions.\"",
                style = TextStyle(
                    fontFamily = workSansSemiBoldFontFamily(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 20.dp)
            )

            // Email TextField
            TextField(
                value = "",
                onValueChange = { /* Handle email input */ },
                label = { Text("E-mail") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Password TextField
            TextField(
                value = "",
                onValueChange = { /* Handle password input */ },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Login Button
            Button(
                onClick = { /* Handle login action */ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)), // Set to green
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
                    fontFamily = lemonadaFontFamily(), // Use Lemonada font
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            )

            Spacer(modifier = Modifier.height(30.dp))
            // Register Link
            TextButton(
                onClick = { /* Handle register click */ },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    text = "Don't have an account? Register",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}
