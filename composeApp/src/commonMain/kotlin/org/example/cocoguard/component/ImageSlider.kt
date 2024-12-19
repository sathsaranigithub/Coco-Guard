package org.example.cocoguard.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coco_guard.composeapp.generated.resources.Res
import coco_guard.composeapp.generated.resources.homemain
import coco_guard.composeapp.generated.resources.slider1
import coco_guard.composeapp.generated.resources.slider2
import coco_guard.composeapp.generated.resources.slider3
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

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