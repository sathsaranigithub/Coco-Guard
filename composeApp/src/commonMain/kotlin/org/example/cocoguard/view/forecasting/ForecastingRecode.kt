package org.example.cocoguard.view.forecasting

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coco_guard.composeapp.generated.resources.Res
import coco_guard.composeapp.generated.resources.homemain
import org.example.cocoguard.model.Demand
import org.example.cocoguard.controller.FirestoreRepository
import org.example.cocoguard.component.HeaderCardOne
import org.jetbrains.compose.resources.painterResource

@Composable
fun ForecastingRecordScreen(navController: NavController, userEmail: String) {
    val repository = remember { FirestoreRepository() }
    var demandRecords by remember { mutableStateOf<List<Demand>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

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
        if (loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator( color = Color(0xFF4CAF50))
            }
        } else if (errorMessage.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "First you need to add Demand forecasting recode and save then you can see the your available recodes with prediction result: $errorMessage", color = Color.Red, fontSize = 16.sp)
            }
        } else {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
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
