package org.example.cocoguard.view

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavController
import coco_guard.composeapp.generated.resources.Res
import coco_guard.composeapp.generated.resources.cardone
import coco_guard.composeapp.generated.resources.cardthree
import coco_guard.composeapp.generated.resources.cardtwo
import org.example.cocoguard.component.ImageCard
import org.example.cocoguard.component.ImageSlider
import org.example.cocoguard.ui.theme.workSansBoldFontFamily


@Composable
fun HomeScreen(navController: NavController, loggedInEmail: String) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val showLogoutDialog = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp)
                .padding(0.dp),
            shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp),
            backgroundColor = Color(0xFF024A1A),
            elevation = 8.dp
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = if (isPressed) Color(0xFF4CAF50) else Color.Transparent, // Change background on press
                            shape = CircleShape
                        )
                ) {
                    IconButton(
                        onClick = {
                            showLogoutDialog.value = true
                        },
                        modifier = Modifier
                            .size(52.dp),
                        interactionSource = interactionSource
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "logout", tint = Color.White)
                    }
                }
                // Title
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color(0xFF4CAF50))) {
                            append("AI Powered\n")
                        }
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append("For Coconut\nFarming")
                        }
                    },
                    fontSize = 30.sp,
                    fontFamily = workSansBoldFontFamily(),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 0.dp, bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Welcome to CocoGuard. Helps you detect coconut diseases early with advanced AI technology, predict demand trends to stay ahead in the market, and yield accurately.",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = workSansBoldFontFamily(),
                        modifier = Modifier
                            .weight(1f)
                    )
                    Card(
                      modifier = Modifier
                            .fillMaxWidth(1 / 3f)
                            .aspectRatio(154f / 114f)
                            .padding(start = 0.dp),
                        shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp),
                        backgroundColor = Color(0xFF024A1A),
                        elevation = 8.dp
                    ) {
                        ImageSlider()
                    }
                }
            }
        }
        // List of images, titles, and descriptions
        val cardData = listOf(
            Pair(Triple(Res.drawable.cardone, "Disease Detection", "Image processing for real-time coconut tree diseases identification and recommended treatment for disease"), "imageUpload/$loggedInEmail"),
            Pair(Triple(Res.drawable.cardtwo, "Demand Forecasting", "Detect coconut diseases early with AI-driven tools."), "forecastingQuestion/$loggedInEmail"),
            Pair(Triple(Res.drawable.cardthree, "Yield Prediction", "Predict coconut yield accurately to enhance productivity."), "yieldQuestion/$loggedInEmail")
        )
        // Scrollable content with image cards
        LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f).padding(top = 16.dp)) {
            items(cardData.size) { index ->
                val (cardInfo, route) = cardData[index]
                val (imageRes, title, description) = cardInfo
                ImageCard(
                    imageRes = imageRes,
                    title = title,
                    description = description,
                    onClick = { navController.navigate(route) }
                )
            }
        }
    }
    if (showLogoutDialog.value) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog.value = false }, // Dismiss dialog on outside tap
            title = {
                Text(text = "Logout",fontFamily = workSansBoldFontFamily(), fontSize = 25.sp, color = Color(0xFF024A1A))
            },
            text = {
                Text(text="Are you sure you want to log out?",fontFamily = workSansBoldFontFamily(), fontSize = 20.sp)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog.value = false // Close dialog
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true } // Clear backstack
                        }
                    }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFBDA83B))
                ) {
                    Text("Yes", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog.value = false } ,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFBDA83B))
                ) {
                    Text("No")
                }
            }
        )
    }
}



