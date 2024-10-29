package org.example.cocoguard.screens.yield

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.TabRowDefaults.Divider
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
import cocoguard.composeapp.generated.resources.Res
import cocoguard.composeapp.generated.resources.homemain
import org.example.cocoguard.ui.theme.workSansBoldFontFamily
import org.jetbrains.compose.resources.painterResource

@Composable
fun YieldRecordScreen() {
    // Existing top card layout
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
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
            // Title
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.White)) {
                        append("Coconut yield\n")
                    }
                    withStyle(style = SpanStyle(color = Color(0xFF4CAF50))) {
                        append("Record history")
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
                    text = "Help you to analyze predicted coconut yield record history",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = workSansBoldFontFamily(),
                    modifier = Modifier.weight(1f)
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

    val records = listOf(
        YieldRecord("Loamy", 6.5, 60, 25, 5, "November", 3, 1488),
        YieldRecord("Loamy", 7.0, 65, 30, 8, "April", 6, 1592),
        YieldRecord("Sandy Loam", 9.1, 80, 30, 5, "November", 8, 1532)
    )

    Column(
        modifier = Modifier
            .padding(top = 240.dp)
            .verticalScroll(rememberScrollState()) // Enables scrolling for the table
    ) {
        // Table Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color(0xFFEBFF9A))
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Soil type", "Soil PH", "Humidity", "Temperature", "Sunlight hours", "Current month", "Plant age", "Prediction Result").forEach { header ->
                Column(
                    modifier = Modifier.weight(1f), // Makes columns equal width
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = header, fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 14.sp)
                }
                Divider(modifier = Modifier.width(1.dp).fillMaxHeight().background(Color.Black))
            }   }

        Divider(color = Color.Gray, thickness = 1.dp)

        // Table Rows
        records.forEachIndexed { index, record ->
            val backgroundColor = if (index % 2 == 0) Color.White else Color(0xFFD8F3DC) // Alternating colors

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor)
                    .padding(end = 5.dp, start = 5.dp),


                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf(
                    record.soilType,
                    record.soilPH.toString(),
                    record.humidity.toString(),
                    record.temperature.toString(),
                    record.sunlightHours.toString(),
                    record.currentMonth,
                    record.plantAge.toString(),
                    record.predictionResult.toString()
                ).forEach { data ->
                    Column(
                        modifier = Modifier.weight(1f), // Ensures equal column width
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = data, color = Color.Black, fontSize = 14.sp)
                    }
                    Divider(modifier = Modifier.width(1.dp).fillMaxHeight().background(Color.Black))
                }    }

            Divider(color = Color.LightGray, thickness = 0.5.dp)
        }
    }
}

data class YieldRecord(
    val soilType: String,
    val soilPH: Double,
    val humidity: Int,
    val temperature: Int,
    val sunlightHours: Int,
    val currentMonth: String,
    val plantAge: Int,
    val predictionResult: Int
)
