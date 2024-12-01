package org.example.cocoguard

import RegisterPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.ButtonDefaults
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.unit.dp
import org.example.cocoguard.auth.LoginPage
import org.example.cocoguard.navigation.navControllerWithNavigation
import org.example.cocoguard.screens.HomeScreen
import org.example.cocoguard.screens.OnboardScreen
import org.example.cocoguard.ui.theme.headingTextStyle
import org.example.cocoguard.ui.theme.lemonadaFontFamily
import org.example.cocoguard.ui.theme.subHeadingTextStyle


@Composable
fun App() {
    // Set up navigation using the helper function
    navControllerWithNavigation()
}

