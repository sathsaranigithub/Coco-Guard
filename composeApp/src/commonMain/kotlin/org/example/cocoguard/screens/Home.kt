// Home.kt
package org.example.cocoguard.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
import androidx.compose.ui.text.withStyle
import cocoguard.composeapp.generated.resources.Res
import cocoguard.composeapp.generated.resources.cardone
import cocoguard.composeapp.generated.resources.cardthree
import cocoguard.composeapp.generated.resources.cardtwo
import cocoguard.composeapp.generated.resources.homemain
import cocoguard.composeapp.generated.resources.login
import cocoguard.composeapp.generated.resources.logout
import org.example.cocoguard.ui.theme.workSansBoldFontFamily
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize()) {

        // Non-scrollable main green card at the top
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp)
                .padding(0.dp),
            shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp),
            backgroundColor = Color(0xFF024A1A),
            elevation = 8.dp
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

                    Image(
                        painter = painterResource(Res.drawable.homemain),
                        contentDescription = "Main",
                        modifier = Modifier
                            .fillMaxWidth(1 / 3f)
                            .aspectRatio(154f / 114f)
                            .padding(start = 0.dp)
                    )
                }
            }
        }

        // List of images, titles, and descriptions
        val cardData = listOf(
            Triple(Res.drawable.cardone, "Disease Detection", "Image processing for real-time coconut tree diseases identification and recommended treatment for disease"),
            Triple(Res.drawable.cardtwo, "Demand Forecasting", "Detect coconut diseases early with AI-driven tools."),
            Triple(Res.drawable.cardthree, "Yield Prediction", "Predict coconut yield accurately to enhance productivity.")
        )

        // Scrollable content with image cards
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 16.dp) // Optional padding between green card and scrollable content
        ) {
            items(cardData.size) { index ->
                val (imageRes, title, description) = cardData[index]
                ImageCard(
                    imageRes = imageRes,
                    title = title,
                    description = description
                )
            }
        }
    }
}

@Composable
fun ImageCard(imageRes: DrawableResource, title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Image taking half of the screen width
            Image(
                painter = painterResource(imageRes),
                contentDescription = null,
                modifier = Modifier
                    .weight(0.5f)
                    .height(180.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
            )

            // Text and Button content taking the other half
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* Handle button click */ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Text(text = "Get Start", color = Color.White)
                }
            }
        }
    }
}