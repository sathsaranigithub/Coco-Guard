package org.example.cocoguard.screens.forecasting
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coco_guard.composeapp.generated.resources.Res
import coco_guard.composeapp.generated.resources.second
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.example.cocoguard.Demand
import org.example.cocoguard.FirestoreRepository
import org.example.cocoguard.screens.component.DropdownQuestion
import org.example.cocoguard.screens.component.HeaderCardTwo
import org.example.cocoguard.screens.component.ResultDialog
import org.example.cocoguard.screens.component.TextFieldQuestion
import org.example.cocoguard.ui.theme.workSansBoldFontFamily
import org.jetbrains.compose.resources.painterResource


@Serializable
data class DemandPredictionResponse(
    val label: Double
)

@Composable
fun ForecastingQuestionScreen(navController: NavHostController, email: String) {
    var showDialog by remember { mutableStateOf(false) }
    var predictionResult by remember { mutableStateOf("") }
    var month by remember { mutableStateOf("May") }
    var region by remember { mutableStateOf("Europe") }
    var coconutExportVolume by remember { mutableStateOf("") }
    var domesticCoconutConsumption by remember { mutableStateOf("") }
    var coconutPriceLocal by remember { mutableStateOf("") }
    var internationalCoconutPrice by remember { mutableStateOf("") }
    var exportDestinationDemand by remember { mutableStateOf("") }
    var currencyExchangeRate by remember { mutableStateOf("") }
    var competitorCountriesProduction by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }
    var showValidationError by remember { mutableStateOf(false) }
    var validationErrorMessage by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()



    val coroutineScope = rememberCoroutineScope()
    val repository = FirestoreRepository()
    val client = HttpClient(CIO) {
        engine {
            requestTimeout = 60_000
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
    }
    // Function to handle submission
    val scope = rememberCoroutineScope()
    fun validateInputs(): Boolean {
        return when {
            month.isBlank() -> {
                validationErrorMessage = "Month is required."
                false
            }
            region.isBlank() -> {
                validationErrorMessage = "Region is required."
                false
            }
            coconutExportVolume .isBlank() -> {
                validationErrorMessage = "Coconut export volume is required."
                false
            }
            domesticCoconutConsumption.isBlank() -> {
                validationErrorMessage = "Domestic coconut consumption is required."
                false
            }
            coconutPriceLocal.isBlank() -> {
                validationErrorMessage = "Local coconut price is required."
                false
            }
            internationalCoconutPrice.isBlank() -> {
                validationErrorMessage = "International coconut price is required."
                false
            }
            exportDestinationDemand.isBlank() -> {
                validationErrorMessage = "Export destination demand is required."
                false
            }
            currencyExchangeRate.isBlank() -> {
                validationErrorMessage = "Currency exchange rate is required."
                false
            }
            competitorCountriesProduction.isBlank() -> {
                validationErrorMessage = "Competitor countries' production is required."
                false
            }

            else -> {
                showValidationError = false // Reset validation error state
                true
            }
        }
    }
    fun submitData() {
        if (validateInputs()) {
            isLoading = true
            scope.launch {
                val request = DemandPredictionRequest(
                    Month = month,
                    Region = region,
                    Coconut_Export_Volume_kg = coconutExportVolume.toDouble(),
                    Domestic_Coconut_Consumption_kg = domesticCoconutConsumption.toDouble(),
                    Coconut_Prices_Local_LKR_per_kg = coconutPriceLocal.toDouble(),
                    International_Coconut_Prices_USD_per_kg = internationalCoconutPrice.toDouble(),
                    Export_Destination_Demand_kg = exportDestinationDemand.toDouble(),
                    Currency_Exchange_Rates_LKR_to_USD = currencyExchangeRate.toDouble(),
                    Competitor_Countries_Production_kg = competitorCountriesProduction.toDouble()
                )
                // Log the request data
                println("Submitting data: $request")
                val response = fetchDemandPrediction(client, request)
                if (response != null) {
                    predictionResult = response.label.toString()
                } else {
                    predictionResult = "Failed to predict,Please ensure your internet connectivity\nTry again later"
                }
                isLoading = false
                showDialog = true
            }
        } else {
            showValidationError = true
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        HeaderCardTwo(
            navController = navController,
            title = "AI-Powered Coconut\n",
            subtitle = "Demand Forecasting Prediction",
            description = "Leveraging AI for coconut demand forecasting provides accurate, data-driven insights into market trends",
            buttonText = "Forecast Record",
            buttonAction = { navController.navigate("forecastingRecord/$email") },
            painter = painterResource(Res.drawable.second),
            isPressed = isPressed
        )


        Spacer(modifier = Modifier.height(16.dp))

        // Questions Section
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                DropdownQuestion(
                    question = "Which month would you like to forecast coconut demand for?",
                    options = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"),
                    hint = "Select answer",
                    onSelect = { month = it }
                )
            }
            item {
                DropdownQuestion(
                    question = "Which region are you planning to export to??",
                    options = listOf("USA", "Sri Lanka", "UK", "Europe","Middle East"),
                    hint = "Select answer",
                    onSelect = { region = it }
                )
            }
            item {
                TextFieldQuestion(
                    question = "What is the coconut export volume (in kg) for the selected month and region?",
                    hint = "e.g. 200000",
                    value = coconutExportVolume,
                    onValueChange = { coconutExportVolume = it }
                )
            }
            item {
                TextFieldQuestion(
                    question = "What is the coconut export volume (in kg) for the selected month and region?",
                    hint = "e.g. 120000",
                    value = domesticCoconutConsumption,
                    onValueChange = { domesticCoconutConsumption = it }
                )
            }
            item {
                TextFieldQuestion(
                    question = "What is the local price of coconuts in your region (in LKR per kg)?",
                    hint = "e.g. 360",
                    value = coconutPriceLocal,
                    onValueChange = { coconutPriceLocal = it }
                )
            }
            item {
                TextFieldQuestion(
                    question = "What is the international price of coconuts (in USD per kg) for the export region?",
                    hint = "e.g. 3.7",
                    value = internationalCoconutPrice,
                    onValueChange = { internationalCoconutPrice = it }
                )
            }
            item {
                TextFieldQuestion(
                    question = "What is the anticipated export destination demand (in kg) for the selected region?",
                    hint = "e.g. 100000",
                    value = exportDestinationDemand,
                    onValueChange = { exportDestinationDemand = it }
                )
            }
            item {
                TextFieldQuestion(
                    question = "What is the current currency exchange rate (LKR to USD)?",
                    hint = "e.g. 310.11",
                    value = currencyExchangeRate,
                    onValueChange = { currencyExchangeRate = it }
                )
            }
            item {
                TextFieldQuestion(
                    question = "What is the production volume (in kg) of competitor countries for the selected month and region?",
                    hint = "e.g. 150000",
                    value = competitorCountriesProduction, onValueChange = { competitorCountriesProduction = it }
                )
            }

            item {
                // Submit Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Validation message on the start of the row
                    if (showValidationError) {
                        Text(
                            text = validationErrorMessage,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(16.dp)
                                .background(Color(0x80FFCECA)) // Light red background for emphasis
                                .padding(8.dp)
                                .weight(1f) // Take up available space
                        )
                    }
                    Button(
                        onClick = { submitData() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
                        modifier = Modifier.width(160.dp)
                    ) {
                        Text(
                            text = "Submit",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
    if (showDialog) {
        ResultDialog(
            title = "Prediction Result",
            resultText = "Demand Forecasting Prediction: $predictionResult kg",
            detailedText = "The forecast predicts that the coconut demand will reach approximately $predictionResult kg for the selected month and region, based on the provided parameters.",
            onDismiss = { showDialog = false },
            onSave = {
                val email = "$email"
                // Create an instance of the Demand class
                val demand = Demand(
                    month = month,
                    region = region,
                    coconutExportVolume = coconutExportVolume,
                    domesticCoconutConsumption = domesticCoconutConsumption,
                    coconutPriceLocal = coconutPriceLocal,
                    internationalCoconutPrice = internationalCoconutPrice,
                    exportDestinationDemand = exportDestinationDemand,
                    currencyExchangeRate = currencyExchangeRate,
                    competitorCountriesProduction = competitorCountriesProduction,
                    predictionResult = predictionResult
                )
                // Log the demand data for debugging
                println("Saving demand data: $demand")
                // Save the demand data using Firestore or other persistence methods
                coroutineScope.launch {
                    repository.addDemand(email,demand)
                }
            }
        )
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x88000000)), // Semi-transparent overlay
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color.Green,
                strokeWidth = 4.dp
            )
        }
    }
}

@Serializable
data class DemandPredictionRequest(
    @SerialName("Month")
    val Month: String,

    @SerialName("Region")
    val Region: String,

    @SerialName("Coconut Export Volume (kg)")
    val Coconut_Export_Volume_kg: Double,

    @SerialName("Domestic Coconut Consumption (kg)")
    val Domestic_Coconut_Consumption_kg: Double,

    @SerialName("Coconut Prices (Local) (LKR/kg)")
    val Coconut_Prices_Local_LKR_per_kg: Double,

    @SerialName("International Coconut Prices (USD/kg)")
    val International_Coconut_Prices_USD_per_kg: Double,

    @SerialName("Export Destination Demand (kg)")
    val Export_Destination_Demand_kg: Double,

    @SerialName("Currency Exchange Rates (LKR to USD)")
    val Currency_Exchange_Rates_LKR_to_USD: Double,

    @SerialName("Competitor Countries' Production (kg)")
    val Competitor_Countries_Production_kg: Double
)

suspend fun fetchDemandPrediction(client: HttpClient, request: DemandPredictionRequest): DemandPredictionResponse? {
    return try {
        client.post("https://asia-south1-plucky-pointer-443915-u7.cloudfunctions.net/coconutdemandforecasting") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body() // Deserialize directly into DemandPredictionResponse
    } catch (e: Exception) {
        println("Error occurred: ${e.message}")
        null
    }
}