package org.example.cocoguard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.ButtonDefaults
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cocoguard.composeapp.generated.resources.Res
import cocoguard.composeapp.generated.resources.compose_multiplatform
import cocoguard.composeapp.generated.resources.mainimage
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import cocoguard.composeapp.generated.resources.lemonada
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.FontResource


// Composable function to define the font family
@OptIn(ExperimentalResourceApi::class)
@Composable
fun lemonadaFontFamily(): FontFamily {
    return FontFamily(
        Font(Res.font.lemonada, FontWeight.Bold)
    )
}
// Define Custom TextStyle for the Heading inside a @Composable function
@Composable
fun headingTextStyle(): TextStyle {
    return TextStyle(
        fontFamily = lemonadaFontFamily(),
        fontSize = 42.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFBAEC6F) // Text color in hexadecimal
    )
}
// Define Custom TextStyle for the Subheading
@Composable
fun subHeadingTextStyle(): TextStyle {
    return TextStyle(
        fontFamily = lemonadaFontFamily(),
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal,
        color = Color(0xFFF5FFE2) ,// Same text color as the heading
    )
}

@Composable
// Main Screen Composable with navigation
fun App() {
    val navController = rememberNavController() // Navigation controller

    NavHost(navController = navController, startDestination = "home") {
        // Home screen route
        composable("home") { HomeScreen(navController) }
        // Login page route
        composable("login") { LoginPage() }
    }
}

// Home screen composable where the "Get Started" button is located
@Composable
fun HomeScreen(navController: NavController) {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize() // Fill the entire screen
        ) {
            // Background Image
            Image(
                painter = painterResource(Res.drawable.mainimage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(), // Fill the entire screen
                contentScale = ContentScale.Crop // Adjust the scaling to crop the image
            )

            // Main Heading Text placed at the top start
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart) // Align the text to the top start
                    .padding(top = 32.dp, start = 15.dp) // Add padding
            ) {
                Text(
                    text = "Coco Guard",
                    style = headingTextStyle(),
                    modifier = Modifier
                        .padding(bottom = 8.dp) // Add some spacing between the heading and subheading
                )

                // Subheading Text
                Text(
                    text = "Empowering coconut farmers with cutting-edge AI technology",
                    style = subHeadingTextStyle(),
                    modifier = Modifier
                        .width(330.dp)
                        .padding(top = 20.dp, start = 15.dp)
                )

                // "Get Started" Button
                Button(
                    onClick = {
                        // Navigate to the login page when clicked
                        navController.navigate("login")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    border = BorderStroke(1.dp, Color.White), // White border
                    modifier = Modifier
                        .padding(top = 20.dp, start = 15.dp)
                ) {
                    Text(
                        text = "Get Started",
                        color = Color.White, // White text color
                        style = TextStyle(fontFamily = lemonadaFontFamily(), fontSize = 18.sp)
                    )
                }
            }
        }
    }
}