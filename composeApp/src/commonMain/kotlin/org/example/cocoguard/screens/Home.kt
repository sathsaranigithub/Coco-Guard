// Home.kt
package org.example.cocoguard.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.withStyle
import cocoguard.composeapp.generated.resources.Res
import cocoguard.composeapp.generated.resources.homemain
import cocoguard.composeapp.generated.resources.logout
import org.example.cocoguard.ui.theme.workSansBoldFontFamily
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(340.dp)
            .padding(0.dp),
        shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp),
        backgroundColor = Color(0xFF024A1A), // Change color as needed
        elevation = 8.dp // Optional elevation for shadow effect
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Logout Icon
            Image(
                painter = painterResource(Res.drawable.logout),
                contentDescription = "Logout",
                modifier = Modifier
                    .size(30.dp)
                    .padding(start = 12.dp, top = 10.dp)
            )

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
                modifier = Modifier.padding(vertical = 10.dp) // Vertical padding for spacing
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp, bottom = 10.dp), // Padding between title and row
                verticalAlignment = Alignment.CenterVertically // Align items vertically
            ) {
                // Description
                Text(
                    text = "Welcome to CocoGuard.Helps you detect coconut diseases early with advanced AI technology, predict demand trends to stay ahead in the market, and yield accurately",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = workSansBoldFontFamily(),
                    modifier = Modifier
                         // Padding between image and text
                        .weight(1f) // Make the text take the remaining space
                )
                // Bus Image
                Image(
                    painter = painterResource(Res.drawable.homemain),
                    contentDescription = "Main",
                    modifier = Modifier
                        .fillMaxWidth(1 / 3f) // Set image width to 3/4 of the Card width
                        .aspectRatio(154f / 114f) // Maintain aspect ratio of the image
                        .padding(start = 0.dp)
                )
            }

        }
    }
}
