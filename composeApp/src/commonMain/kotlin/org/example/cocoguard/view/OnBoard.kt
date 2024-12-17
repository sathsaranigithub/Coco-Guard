package org.example.cocoguard.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coco_guard.composeapp.generated.resources.Res
import coco_guard.composeapp.generated.resources.mainimage
import org.example.cocoguard.ui.theme.headingTextStyle
import org.example.cocoguard.ui.theme.lemonadaFontFamily
import org.example.cocoguard.ui.theme.subHeadingTextStyle
import org.jetbrains.compose.resources.painterResource

@Composable
fun OnboardScreen(navController: NavController) {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background Image
            Image(
                painter = painterResource(Res.drawable.mainimage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Main Heading Text placed at the top start
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 32.dp, start = 15.dp)
            ) {
                Text(
                    text = "Coco Guard",
                    style = headingTextStyle(),
                    modifier = Modifier
                        .padding(bottom = 8.dp, start = 15.dp)
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
                        navController.navigate("login")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    border = BorderStroke(1.dp, Color.White),
                    modifier = Modifier
                        .padding(top = 20.dp, start = 15.dp)
                ) {
                    Text(
                        text = "Get Started",
                        color = Color.White,
                        style = TextStyle(fontFamily = lemonadaFontFamily(), fontSize = 18.sp)
                    )
                }
            }
        }
    }
}
