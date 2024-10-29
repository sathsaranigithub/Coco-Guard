package org.example.cocoguard.screens.forecasting
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import cocoguard.composeapp.generated.resources.Res
import cocoguard.composeapp.generated.resources.cam
import cocoguard.composeapp.generated.resources.gallery
import cocoguard.composeapp.generated.resources.homemain
import cocoguard.composeapp.generated.resources.uploadimage
import org.example.cocoguard.ui.theme.workSansBoldFontFamily
import org.jetbrains.compose.resources.painterResource

@Composable
fun ForecastingQuestionScreen(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
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
                            text = "Leveraging artificial intelligence for coconut demand forecasting provides accurate, data-driven insights into market trends",
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
                            modifier = Modifier.width(180.dp)
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
                    hint = "Select answer"
                )
            }
            item {
                TextFieldQuestion(
                    question = "What is the soil pH level?",
                    hint = "e.g. 6.5"
                )
            }
            item {
                TextFieldQuestion(
                    question = "What is the humidity percentage?",
                    hint = "e.g. 60"
                )
            }
            item {
                TextFieldQuestion(
                    question = "What is the temperature (°C)?",
                    hint = "e.g. 25"
                )
            }
            item {
                TextFieldQuestion(
                    question = "How many sunlight hours does the plant receive daily?",
                    hint = "e.g. 5"
                )
            }
            item {
                DropdownQuestion(
                    question = "What is the month?",
                    options = listOf("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"),
                    hint = "Select answer"
                )
            }
            item {
                TextFieldQuestion(
                    question = "What is the age of the coconut plant (in years)?",
                    hint = "e.g. 3"
                )
            }
            item {
                // Submit Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { showDialog = true },
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
        YieldResultDialog(
            onDismiss = { showDialog = false },
            onSave = {
                // Handle save action here
                showDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownQuestion(question: String, options: List<String>, hint: String) {
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
            OutlinedTextField(
                value = selectedOption,
                onValueChange = { },
                readOnly = true,
                label = { Text(text = hint) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            selectedOption = option
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
fun TextFieldQuestion(question: String, hint: String) {
    Column {
        Text(
            text = question,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        androidx.compose.material.OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(text = hint, color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        )
    }
}
@Composable
fun YieldResultDialog(onDismiss: () -> Unit, onSave: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Yield Prediction Result")
        },
        text = {
            Column {
                Text(text = "Predicted Yield: 1200 ")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "This result is based on the input factors you provided.")
            }
        },
        confirmButton = {
            TextButton(
                onClick = onSave
            ) {
                Text(text = "Save", color = Color(0xFF4CAF50))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = "Close", color = Color.Red)
            }
        }
    )
}
