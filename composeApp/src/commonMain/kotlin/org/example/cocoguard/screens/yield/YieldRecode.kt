package org.example.cocoguard.screens.yield

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coco_guard.composeapp.generated.resources.Res
import coco_guard.composeapp.generated.resources.first
import coco_guard.composeapp.generated.resources.homemain
import org.example.cocoguard.Demand
import org.example.cocoguard.FirestoreRepository
import org.example.cocoguard.Yield
import org.example.cocoguard.screens.component.HeaderCardOne
import org.example.cocoguard.ui.theme.workSansBoldFontFamily
import org.jetbrains.compose.resources.painterResource

@Composable
fun YieldRecordScreen(navController: NavController, userEmail: String) {
    val repository = remember { FirestoreRepository() }
    var yieldRecords by remember { mutableStateOf<List<Yield>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    // Existing top card layout
    // Fetch demands
    LaunchedEffect(userEmail) {
        try {
            loading = true
            val result = repository.getYield(userEmail)
            if (result.isSuccess) {
                yieldRecords = result.getOrNull() ?: emptyList()
            } else {
                errorMessage = result.exceptionOrNull()?.message ?: "Unknown error"
            }
        } catch (e: Exception) {
            errorMessage = e.message ?: "Unexpected error occurred"
        } finally {
            loading = false
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        HeaderCardOne(
            title = "Coconut Yield Prediction\n",
            subtitle = "Record History",
            sentence = "Help you to analyze predicted coconut yield record history",
            navController = navController,
            painter = painterResource(Res.drawable.homemain),
            onBackClick = {
                navController.navigate("home")
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        // Display Loading or Error
        if (loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (errorMessage.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: $errorMessage", color = Color.Red, fontSize = 16.sp)
            }
        } else {
            // Display Table of Records
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                // Table Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFEBFF9A))
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf(
                        "Soil type",
                        "Soil PH",
                        "Humidity",
                        "Temperature",
                        "Sunlight hours",
                        "Month",
                        "Plant age",
                        "Yield prediction"
                    ).forEach { header ->
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = header,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontSize = 14.sp
                            )
                        }
                        androidx.compose.material.Divider(
                            modifier = Modifier.width(1.dp).fillMaxHeight().background(Color.Black)
                        )
                    }
                }

                androidx.compose.material.Divider(color = Color.Gray, thickness = 1.dp)

                // Table Rows
                yieldRecords.forEachIndexed { index, record ->
                    val backgroundColor = if (index % 2 == 0) Color.White else Color(0xFFD8F3DC)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(backgroundColor)
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf(
                            record.soilType,
                            record.soilPH,
                            record.humidity,
                            record.temperature,
                            record.sunlightHours,
                            record.month,
                            record.plantAge,
                            record.predictionResult
                        ).forEach { data ->
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = data, color = Color.Black, fontSize = 14.sp)
                            }
                            androidx.compose.material.Divider(
                                modifier = Modifier.width(1.dp).fillMaxHeight()
                                    .background(Color.Black)
                            )
                        }
                    }
                    androidx.compose.material.Divider(color = Color.LightGray, thickness = 0.5.dp)
                }
            }
        }
    }
}
