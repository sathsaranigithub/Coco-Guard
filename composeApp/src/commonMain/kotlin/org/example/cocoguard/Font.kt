package org.example.cocoguard

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
//import bigboss.shared.generated.resources.Res
//import bigboss.shared.generated.resources.teko_bold
//import bigboss.shared.generated.resources.teko_light
//import bigboss.shared.generated.resources.teko_medium
//import bigboss.shared.generated.resources.teko_regular
//import bigboss.shared.generated.resources.teko_semibold
import cocoguard.composeapp.generated.resources.Res
import cocoguard.composeapp.generated.resources.lemonada
import cocoguard.composeapp.generated.resources.worksansblack
import cocoguard.composeapp.generated.resources.worksansbold
import cocoguard.composeapp.generated.resources.worksansregular
import cocoguard.composeapp.generated.resources.worksanssemibold
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TekoFontFamily() = FontFamily(
    Font(Res.font.lemonada, weight = FontWeight.Light),
    Font(Res.font.worksansblack, weight = FontWeight.Bold),
    Font(Res.font.worksanssemibold, weight = FontWeight.Bold),
    Font(Res.font.lemonada, weight = FontWeight.Normal),
    Font(Res.font.lemonada, weight = FontWeight.Medium),
    Font(Res.font.lemonada, weight = FontWeight.SemiBold),
    Font(Res.font.lemonada, weight = FontWeight.Bold)
)

