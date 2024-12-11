package org.example.cocoguard.screens.forecasting

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
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
import coco_guard.composeapp.generated.resources.homemain
import kotlinx.coroutines.launch
import org.example.cocoguard.Demand
import org.example.cocoguard.FirestoreRepository
import org.example.cocoguard.screens.component.HeaderCardOne
import org.example.cocoguard.ui.theme.workSansBoldFontFamily
import org.jetbrains.compose.resources.painterResource

@Composable
fun ForecastingRecordScreen(navController: NavController, userEmail: String) {
    val repository = remember { FirestoreRepository() }
    var demandRecords by remember { mutableStateOf<List<Demand>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()


    // Fetch demands
    LaunchedEffect(userEmail) {
        try {
            loading = true
            val result = repository.getDemands(userEmail)
            if (result.isSuccess) {
                demandRecords = result.getOrNull() ?: emptyList()
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
            title = "Coconut Demand Forecast\n",
            subtitle = "Record History",
            sentence = "Enabling producers and distributors to make informed decisions",
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
                        "Month", "Region", "Export (kg)", "Consumption (kg)",
                        "Price (LKR/kg)", "Intl. Price (USD/kg)", "Export Demand (kg)",
                        "Exchange Rate", "Competitor Production (kg)", "Demand forecasting"
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
                        Divider(
                            modifier = Modifier.width(1.dp).fillMaxHeight().background(Color.Black)
                        )
                    }
                }

                Divider(color = Color.Gray, thickness = 1.dp)

                // Table Rows
                demandRecords.forEachIndexed { index, record ->
                    val backgroundColor = if (index % 2 == 0) Color.White else Color(0xFFD8F3DC)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(backgroundColor)
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf(
                            record.month,
                            record.region,
                            record.coconutExportVolume,
                            record.domesticCoconutConsumption,
                            record.coconutPriceLocal,
                            record.internationalCoconutPrice,
                            record.exportDestinationDemand,
                            record.currencyExchangeRate,
                            record.competitorCountriesProduction,
                            record.predictionResult
                        ).forEach { data ->
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = data, color = Color.Black, fontSize = 14.sp)
                            }
                            Divider(
                                modifier = Modifier.width(1.dp).fillMaxHeight()
                                    .background(Color.Black)
                            )
                        }
                    }
                    Divider(color = Color.LightGray, thickness = 0.5.dp)
                }
            }
        }
    }
}
