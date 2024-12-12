package org.example.cocoguard.screens.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
@Composable
fun SideImage(painter: Painter,contentDescription: String) {
    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.3f),
        contentScale = ContentScale.Crop
    )
}
