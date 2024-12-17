package org.example.cocoguard.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.NavController

@Composable
fun HeaderCardOne(
    title: String,
    subtitle: String,
    sentence: String,
    navController: NavController,
    painter: Painter,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(270.dp)
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
                        color = if (isPressed) Color(0xFF4CAF50) else Color.Transparent, // Change background on press
                        shape = CircleShape
                    )
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(48.dp),
                    interactionSource = interactionSource
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
            // Title
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
                modifier = Modifier.padding(vertical = 0.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = sentence,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = workSansBoldFontFamily(),
                    modifier = Modifier.weight(1f)
                )
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
