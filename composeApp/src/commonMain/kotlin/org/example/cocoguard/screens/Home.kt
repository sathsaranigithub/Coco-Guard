// Home.kt
package org.example.cocoguard.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
import coco_guard.composeapp.generated.resources.Res
import coco_guard.composeapp.generated.resources.cardone
import coco_guard.composeapp.generated.resources.cardthree
import coco_guard.composeapp.generated.resources.cardtwo
import coco_guard.composeapp.generated.resources.homemain
import coco_guard.composeapp.generated.resources.logout
import coco_guard.composeapp.generated.resources.slider1
import coco_guard.composeapp.generated.resources.slider2
import coco_guard.composeapp.generated.resources.slider3
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.cocoguard.ui.theme.workSansBoldFontFamily
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen(navController: NavController, loggedInEmail: String) {
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
            Pair(Triple(Res.drawable.cardone, "Disease Detection", "Image processing for real-time coconut tree diseases identification and recommended treatment for disease"), "imageUpload"),
            Pair(Triple(Res.drawable.cardtwo, "Demand Forecasting", "Detect coconut diseases early with AI-driven tools."), "forecastingQuestion/$loggedInEmail"),
            Pair(Triple(Res.drawable.cardthree, "Yield Prediction", "Predict coconut yield accurately to enhance productivity."), "yieldQuestion/$loggedInEmail")
        )


        // Scrollable content with image cards
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 16.dp) // Optional padding between green card and scrollable content
        ) {
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
}

@Composable
fun ImageCard(imageRes: DrawableResource, title: String, description: String,onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
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
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Text(text = "Get Start", color = Color.White)
                }
            }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider() {
    val imagesList = listOf(
        Res.drawable.homemain,
        Res.drawable.slider1,
        Res.drawable.slider2,
        Res.drawable.slider3
    )

    val pageState = rememberPagerState(pageCount = { imagesList.size })
    val coroutineScope = rememberCoroutineScope()

    // Auto-scroll
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            coroutineScope.launch {
                val nextPage = (pageState.currentPage + 1) % imagesList.size
                pageState.animateScrollToPage(nextPage)
            }
        }
    }

    Box( modifier = Modifier
        .fillMaxWidth(1 / 3f)
        .aspectRatio(154f / 114f)
        .padding(start = 0.dp), contentAlignment = Alignment.CenterEnd) {
        HorizontalPager(state = pageState) { page ->
            Image(
                painter = painterResource(imagesList[page]),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(2.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(imagesList.size) { iteration ->
                val color =
                    if (pageState.currentPage == iteration) Color.White else Color.Gray
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(12.dp)
                )
            }
        }
    }
}