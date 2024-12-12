package org.example.cocoguard.screens.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.cocoguard.ui.theme.workSansBoldFontFamily
import org.example.cocoguard.ui.theme.workSansSemiBoldFontFamily
@Composable
fun HeaderText(title: String, subtitle: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = title,
            style = TextStyle(
                fontFamily = workSansBoldFontFamily(),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(bottom = 40.dp)
        )
        Text(
            text = subtitle,
            style = TextStyle(
                fontFamily = workSansSemiBoldFontFamily(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(bottom = 20.dp)
        )
    }
}
