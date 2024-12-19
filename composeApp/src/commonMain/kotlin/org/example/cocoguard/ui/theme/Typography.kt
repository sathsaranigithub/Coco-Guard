package org.example.cocoguard.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coco_guard.composeapp.generated.resources.Res
import coco_guard.composeapp.generated.resources.lemonada
import coco_guard.composeapp.generated.resources.worksansbold
import coco_guard.composeapp.generated.resources.worksansmedium
import coco_guard.composeapp.generated.resources.worksanssemibold
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalResourceApi::class)
@Composable
fun lemonadaFontFamily() = FontFamily(
    Font(Res.font.lemonada, weight = FontWeight.Normal),
    Font(Res.font.lemonada, weight = FontWeight.Bold)
)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun workSansBoldFontFamily() = FontFamily(
    Font(Res.font.worksansbold, FontWeight.Bold)
)

@OptIn(ExperimentalResourceApi::class)
@Composable
fun workSansSemiBoldFontFamily() = FontFamily(
    Font(Res.font.worksanssemibold, FontWeight.SemiBold)
)
@OptIn(ExperimentalResourceApi::class)
@Composable
fun workSansFontFamily() = FontFamily(
    Font(Res.font.worksansmedium, FontWeight.SemiBold)
)
@Composable
fun headingTextStyle() = TextStyle(
    fontFamily = lemonadaFontFamily(),
    fontSize = 42.sp,
    fontWeight = FontWeight.Bold,
    color = Color(0xFFBAEC6F)
)
@Composable
fun subHeadingTextStyle() = TextStyle(
    fontFamily = lemonadaFontFamily(),
    fontSize = 20.sp,
    fontWeight = FontWeight.Normal,
    color = Color(0xFFF5FFE2)

)

