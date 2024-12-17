package org.example.cocoguard.view.yield

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.example.cocoguard.controller.FirestoreRepository
import org.example.cocoguard.model.Yield

import org.example.cocoguard.model.YieldPredictionRequest
import org.example.cocoguard.component.DropdownQuestion
import org.example.cocoguard.component.HeaderCardTwo
import org.example.cocoguard.component.ResultDialog
import org.example.cocoguard.component.TextFieldQuestion
import org.example.cocoguard.controller.YieldFeature
import org.example.cocoguard.utils.validateInputsyield
import org.jetbrains.compose.resources.painterResource

@Serializable
data class YieldPredictionResponse(
    val label: Double
)
@Composable
fun YieldQuestionScreen(navController: NavController, email: String) {
    var showDialog by remember { mutableStateOf(false) }
    var predictionResult by remember { mutableStateOf("") }
    var soilType by remember { mutableStateOf("Loamy") }
    var soilPH by remember { mutableStateOf("") }
    var humidity by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }
    var sunlightHours by remember { mutableStateOf("") }
    var month by remember { mutableStateOf("January") }
    var plantAge by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showValidationError by remember { mutableStateOf(false) }
    var validationErrorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    val repository = FirestoreRepository()
   val yield = YieldFeature()
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
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
    fun submitData() {
        val (isValid, errorMessage) = validateInputsyield(
            soilPH = soilPH,
            humidity = humidity,
            temperature = temperature,
            sunlightHours = sunlightHours,
            plantAge = plantAge,
            )
        if (isValid) {
        isLoading = true
        scope.launch {
            val request = YieldPredictionRequest(
                soil_type_input = soilType,
                soil_pH_input = soilPH.toDoubleOrNull() ?: 0.0,
                humidity_input = humidity.toIntOrNull() ?: 0,
                temperature_input = temperature.toIntOrNull() ?: 0,
                sunlight_hours_input = sunlightHours.toIntOrNull() ?: 0,
                month_input = month,
                plant_age_input = plantAge.toIntOrNull() ?: 0
            )
            // Log the request data
            println("Submitting data: $request")
            val response = yield.fetchYieldPrediction(client, request)
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
            validationErrorMessage = errorMessage
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        HeaderCardTwo(
            navController = navController,
            title = "AI-Powered Coconut\n",
            subtitle = "Yield Prediction",
            description = "Examination of the critical factors influencing coconut yield and analyze improving techniques.",
            buttonText = "Yield Record",
            buttonAction = { navController.navigate("yieldRecord/$email") },
            painter = painterResource(Res.drawable.homemain),
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
                    question = "What type of soil is the coconut plant grown in?",
                    options = listOf("Loamy", "Sandy", "Clay", "Silty"),
                    hint = "Select answer",
                    onSelect = { soilType = it }
                )
            }
            item {
                TextFieldQuestion(
                    question = "What is the soil pH level?",
                    hint = "e.g. 6.5",
                    value = soilPH,
                    onValueChange = { soilPH = it }
                )
            }
            item {
                TextFieldQuestion(
                    question = "What is the humidity percentage?",
                    hint = "e.g. 60",
                    value = humidity,
                    onValueChange = { humidity = it }
                )
            }
            item {
                TextFieldQuestion(
                    question = "What is the temperature (°C)?",
                    hint = "e.g. 25",
                    value = temperature,
                    onValueChange = { temperature = it }
                )
            }
            item {
                TextFieldQuestion(
                    question = "How many sunlight hours does the plant receive daily?",
                    hint = "e.g. 5",
                    value = sunlightHours,
                    onValueChange = { sunlightHours = it }
                )
            }
            item {
                DropdownQuestion(
                    question = "What is the month?",
                    options = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"),
                    hint = "Select answer",
                    onSelect = { month = it }
                )
            }
            item {
                TextFieldQuestion(
                    question = "What is the age of the coconut plant (in years)?",
                    hint = "e.g. 3",
                    value = plantAge,
                    onValueChange = { plantAge = it }
                )
            }
            item {
                // Submit Button
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically)
                {
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
                        Text(text = "Submit", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
    if (showDialog) {
        ResultDialog(
            title = "Prediction Result",
            resultText = "Yield Prediction: $predictionResult kg",
            detailedText = "Predict that the yield per tree can provide the number of kilograms $predictionResult kg  of coconuts produced within a year",
            onDismiss = { showDialog = false },
            onSave = {
                val email = email
                // Create an instance of the yield class
                val yield = Yield(soilType,soilPH,humidity,temperature,sunlightHours,month,plantAge,predictionResult)
                // Log the yield data for debugging
                println("Saving yield data: $yield")
                // Save the yield data using Firestore or other persistence methods
                coroutineScope.launch {
                    repository.addYield(email,yield)
                }
            }
        )
    }
    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize().background(Color(0x88000000)), contentAlignment = Alignment.Center)
        {
            CircularProgressIndicator(color = Color.Green, strokeWidth = 4.dp)
        }
    }
}

