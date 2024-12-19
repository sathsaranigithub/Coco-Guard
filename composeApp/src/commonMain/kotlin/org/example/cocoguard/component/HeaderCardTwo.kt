package org.example.cocoguard.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.cocoguard.ui.theme.workSansBoldFontFamily
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun HeaderCardTwo(
    navController: androidx.navigation.NavController,
    title: String,
    subtitle: String,
    description: String,
    buttonText: String,
    buttonAction: () -> Unit,
    painter: Painter,
    isPressed: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .padding(0.dp),
        shape = RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp),
        backgroundColor = Color(0xFF024A1A),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(
                        color = if (isPressed) Color(0xFF4CAF50) else Color.Transparent,
                        shape = CircleShape
                    )
            ) {
                IconButton(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.White)) {
                        append(title)
                    }
                    withStyle(style = SpanStyle(color = Color(0xFF4CAF50))) {
                        append(subtitle)
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
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = description,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = workSansBoldFontFamily(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    Button(
                        onClick = buttonAction,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFBDA83B)),
                        modifier = Modifier.width(190.dp)
                    ) {
                        Text(
                            text = buttonText,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Image(
                    painter = painter,
                    contentDescription = "Main",
                    modifier = Modifier
                        .fillMaxWidth(1 / 3f)
                        .aspectRatio(154f / 114f)
                        .padding(start = 0.dp)
                )
            }
        }
    }
}