package org.example.cocoguard.screens.forecasting
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import org.example.cocoguard.screens.yield.YieldPredictionRequest
import org.example.cocoguard.screens.yield.YieldPredictionResponse
import org.example.cocoguard.screens.yield.YieldResultDialog
import org.example.cocoguard.screens.yield.fetchYieldPrediction
import org.example.cocoguard.ui.theme.workSansBoldFontFamily
import org.example.cocoguard.ui.theme.workSansFontFamily
import org.jetbrains.compose.resources.painterResource


@Serializable
data class DemandPredictionResponse(
    val label: Double
)

@Composable
fun ForecastingQuestionScreen(navController: NavController) {
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

        // Existing top card layout
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(290.dp)
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
                            append("AI-Powered Coconut")
                        }
                        withStyle(style = SpanStyle(color = Color(0xFF4CAF50))) {
                            append(" Demand Forecasting Prediction")
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
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = "Leveraging AI for coconut demand forecasting provides accurate, data-driven insights into market trends",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = workSansBoldFontFamily(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )

                        Button(
                            onClick = { navController.navigate("forecastingRecord") },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFBDA83B)),
                            modifier = Modifier.width(190.dp)
                        ) {
                            Text(
                                text = "Forecast Record",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Image(
                        painter = painterResource(Res.drawable.second),
                        contentDescription = "Main",
                        modifier = Modifier
                            .fillMaxWidth(1 / 3f)
                            .aspectRatio(154f / 114f)
                            .padding(start = 0.dp)
                    )
                }
            }
        }

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
        DemandResultDialog(
            result = predictionResult,
            onDismiss = { showDialog = false },
            onSave = { /* Handle save logic */ }
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownQuestion(question: String, options: List<String>, hint: String, onSelect: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(hint) }
    Column {
        Text(
            text = question,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedOption,
                onValueChange = { },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(10.dp)),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    focusedIndicatorColor = Color.Transparent, // Dark green
                    unfocusedIndicatorColor = Color.Transparent, // No underline when not focused
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Red
                ),
                shape = RoundedCornerShape(10.dp)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOption = option
                            onSelect(option)
                            expanded = false
                        }
                    ) {
                        Text(text = option)
                    }
                }
            }
        }
    }
}
@Composable
fun TextFieldQuestion(question: String, hint: String, value: String, onValueChange: (String) -> Unit) {
    Column {
        Text(
            text = question,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = hint, color = Color(0xFF4CAF50)) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(10.dp)),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Red
            ),
            shape = RoundedCornerShape(10.dp)
        )
    }
}
@Composable
fun DemandResultDialog(result: String, onDismiss: () -> Unit, onSave: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Prediction Result", fontFamily = workSansBoldFontFamily(), fontSize = 25.sp, color = Color(0xFF024A1A)) },
        text = {
            Column {
                Text(
                    text = "The forecast predicts that the coconut demand will reach approximately $result kg for the selected month and region, based on the provided parameters.",
                    fontFamily = workSansBoldFontFamily(),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Demand Forecasting Prediction: $result kg",
                    fontFamily = workSansBoldFontFamily(),
                    fontSize = 20.sp,
                    color = Color(0xFF024A1A)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFBDA83B)),) {
                Text("Ok")
            }
        },
        dismissButton = {
            Button(onClick = onSave,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFBDA83B)),) {
                Text("Save")
            }
        }
    )
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