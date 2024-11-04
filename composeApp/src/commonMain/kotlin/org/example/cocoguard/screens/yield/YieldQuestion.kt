package org.example.cocoguard.screens.yield

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import cocoguard.composeapp.generated.resources.Res
import cocoguard.composeapp.generated.resources.baseline_arrow_drop_down_24
import cocoguard.composeapp.generated.resources.homemain
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.kotlinx.serializer.KotlinxSerializer
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.example.cocoguard.ui.theme.workSansBoldFontFamily
import org.jetbrains.compose.resources.painterResource

@Serializable
data class YieldPredictionResponse(
    val label: Double // Change to Double to match the JSON input
)
@Composable
fun YieldQuestionScreen(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    var predictionResult by remember { mutableStateOf("") }

    // State holders for each input field
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
            soilPH.isBlank() -> {
                validationErrorMessage = "Soil pH is required."
                false
            }
            soilPH.toDoubleOrNull() == null -> {
                validationErrorMessage = "Soil pH must be a valid number."
                false
            }
            humidity.isBlank() -> {
                validationErrorMessage = "Humidity percentage is required."
                false
            }
            humidity.toIntOrNull() == null -> {
                validationErrorMessage = "Humidity must be a valid number."
                false
            }
            temperature.isBlank() -> {
                validationErrorMessage = "Temperature is required."
                false
            }
            temperature.toIntOrNull() == null -> {
                validationErrorMessage = "Temperature must be a valid number."
                false
            }
            sunlightHours.isBlank() -> {
                validationErrorMessage = "Sunlight hours are required."
                false
            }
            sunlightHours.toIntOrNull() == null -> {
                validationErrorMessage = "Sunlight hours must be a valid number."
                false
            }
            plantAge.isBlank() -> {
                validationErrorMessage = "Plant age is required."
                false
            }
            plantAge.toIntOrNull() == null -> {
                validationErrorMessage = "Plant age must be a valid number."
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
            val response = fetchYieldPrediction(client, request)
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
                .height(270.dp)
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
                            append(" Yield Prediction")
                        }
                    },
                    fontSize = 30.sp,
                    fontFamily = workSansBoldFontFamily(),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 10.dp)
                )

                // Card Content
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
                            text = "Examination of the critical factors influencing coconut yield and suggest improving techniques.",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = workSansBoldFontFamily(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp)
                        )

                        Button(
                            onClick = { navController.navigate("yieldRecord") },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFBDA83B)),
                            modifier = Modifier.width(160.dp)
                        ) {
                            Text(
                                text = "Yield Record",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

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
//    if (showValidationError) {
//        Text(
//            text = validationErrorMessage,
//            color = Color.Red,
//            fontSize = 14.sp,
//            modifier = Modifier
//                .padding(16.dp)
//                .background(Color(0x80FFCECA)) // Light red background for emphasis
//                .padding(8.dp)
//        )
//    }

    if (showDialog) {
        YieldResultDialog(
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
//                label = { Text(hint) },
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
fun YieldResultDialog(result: String, onDismiss: () -> Unit, onSave: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Prediction Result", fontFamily = workSansBoldFontFamily(), fontSize = 25.sp, color = Color(0xFF024A1A)) },
        text = { Text("Yield Prediction: $result",
             fontFamily = workSansBoldFontFamily(), fontSize = 20.sp) },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFBDA83B)),) {
                Text("OK")
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


// Remember to define your YieldPredictionRequest data class
@Serializable
data class YieldPredictionRequest(
    val soil_type_input: String,
    val soil_pH_input: Double,
    val humidity_input: Int,
    val temperature_input: Int,
    val sunlight_hours_input: Int,
    val month_input: String,
    val plant_age_input: Int
)
// Define fetchYieldPrediction function as per your implementation
suspend fun fetchYieldPrediction(client: HttpClient, request: YieldPredictionRequest): YieldPredictionResponse? {
    return try {
        client.post("https://us-central1-tea-factory-management-system.cloudfunctions.net/yieldprediction") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body() // Deserialize directly into YieldPredictionResponse
    } catch (e: Exception) {
        println("Error occurred: ${e.message}")
        null

    }
}
