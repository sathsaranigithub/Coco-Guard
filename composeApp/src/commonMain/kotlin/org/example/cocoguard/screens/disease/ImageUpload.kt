package org.example.cocoguard.screens.disease


import androidx.compose.foundation.Image
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cocoguard.composeapp.generated.resources.Res
import cocoguard.composeapp.generated.resources.gallery
import cocoguard.composeapp.generated.resources.homemain
import cocoguard.composeapp.generated.resources.uploadimage
import coil3.compose.AsyncImage
import com.mohamedrejeb.calf.io.getPath
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.example.cocoguard.ui.theme.workSansBoldFontFamily
import org.jetbrains.compose.resources.painterResource
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextButton

@Serializable
data class DetectionResult(val `class`: String) // Use backticks if "class" is a reserved keyword

// Add this function to parse the JSON response
fun parseDiseaseFromResponse(response: String): String {
    return try {
        val detectionResult = Json.decodeFromString<DetectionResult>(response)
        detectionResult.`class`
    } catch (e: Exception) {
        "Unknown disease" // Handle parsing errors
    }
}
@Composable
fun ImageUploadScreen() {
    val scope = rememberCoroutineScope()
    val context = com.mohamedrejeb.calf.core.LocalPlatformContext.current
    var byteArray = remember { mutableStateOf(ByteArray(0)) }
    var platformSpecificFilePath = remember { mutableStateOf("") }
    var responseText = remember { mutableStateOf<String?>(null) }
    var isLoading = remember { mutableStateOf(false) }
    var detectedDisease = remember { mutableStateOf("") }
    var showDialog = remember { mutableStateOf(false) }


    // Use a lambda to configure the file picker launcher
    val pickerLauncher = rememberFilePickerLauncher(
        onResult = { files ->
            scope.launch{
                files.firstOrNull()?.let {
                    byteArray.value = it.readByteArray(context)
                    platformSpecificFilePath.value = it.getPath(context)?:""
                }
            }

        },
        type = FilePickerFileType.Image,
        selectionMode = FilePickerSelectionMode.Single
    )

    Column(modifier = Modifier.fillMaxSize()) {

        // Existing top card layout
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
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
                            append("Coconut Trees Diseases")
                        }
                        withStyle(style = SpanStyle(color = Color(0xFF4CAF50))) {
                            append(" Identification")
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
                        text = "Upload an image to detect coconut tree diseases and receive AI-driven treatment suggestions.",
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

        // Row layout with image card and upload button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Image card
            Card(
                modifier = Modifier
                    .size(180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                backgroundColor = Color.Gray,
                elevation = 4.dp
            ) {
                // Check if byteArray has any data, if not show the static image
                if (byteArray.value.isNotEmpty()) {
                    // If the byteArray has data, display it as an image
                    AsyncImage(
                        model = byteArray.value,
                        modifier = Modifier.size(170.dp),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                } else {
                    // If no image has been uploaded, show the static image
                    Image(
                        painter = painterResource(Res.drawable.uploadimage), // Replace with your actual resource
                        contentDescription = "Upload Image Placeholder",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            // Text area with upload button
            Column(
               modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Upload Image",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "You can select an existing photo to upload.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(Res.drawable.gallery),
                        contentDescription = "Gallery",
                        modifier = Modifier.size(40.dp).clickable {
                            pickerLauncher.launch()
                        }
                    )
                }

                Button(
                    onClick = {
                        // Start the upload and POST request
                        if (byteArray.value.isNotEmpty()) {
                            scope.launch {
                                isLoading.value = true
                                val response = uploadImage(byteArray.value)
                                responseText.value = response // Store the full response text
                                detectedDisease.value = parseDiseaseFromResponse(response) // Parse the disease
                                isLoading.value = false
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
                    modifier = Modifier
                        .width(300.dp)
                        .padding(top = 16.dp)
                ) {
                    Text(text = "Upload", color = Color.White)
                }
                if (isLoading.value) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(2.dp),
                        color = Color(0xFF4CAF50))
                }
            }
        }
        LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .weight(1f)
            ) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            if (detectedDisease.value.isNotEmpty()) {

                                if(detectedDisease.value != "Unknown disease" && detectedDisease.value != "Unknown Class"){
                                Text(
                                    text = "Detected disease: ${detectedDisease.value}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Description",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = when (detectedDisease.value) {
                                        "Red rust" -> "Red Rust is caused by an alga, Cephaleuros virescens. This disease commonly affects leaves, young stems, and twigs, causing a reddish appearance on the plant."
                                        "Blister Blight" -> "Blister Blight is caused by the fungus Exobasidium vexans, primarily affecting young leaves. This disease is severe in areas with high humidity."
                                        "Brown Blight" -> "Brown Blight is caused by the fungus Colletotrichum camelliae. It affects leaves, stems, and even buds in severe cases, often appearing in humid and rainy conditions."
                                        else -> "Description of the diseases"
                                    },
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Symptoms",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text =  when (detectedDisease.value) {
                                        "Red rust" -> "Orange or reddish patches on leaves and stems.\nAffected leaves may become stunted, and severe infestations can lead to defoliation."
                                        "Blister Blight" -> "Small, translucent blisters form on the upper side of young leaves.\nThese blisters enlarge and turn brown, ultimately leading to leaf deformation and tissue death."
                                        "Brown Blight" -> "Circular brown spots on leaves, sometimes with yellow halos.\nLeaf edges may become ragged, and in severe cases, whole leaves turn brown and fall off."
                                        else -> "Symptoms of the diseases"
                                    }, fontSize = 14.sp,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Treatment",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(

                                    text = when (detectedDisease.value) {
                                        "Red rust" -> "Cultural: Prune heavily infected parts of the plant to remove sources of the disease.\nChemical: Apply copper-based fungicides or algaecides to prevent and reduce infection. Consistent monitoring is necessary, especially during wet seasons when the disease is more active."
                                        "Blister Blight" -> "Cultural: Maintain optimal spacing between plants to reduce humidity and improve airflow.\nChemical: Spray with fungicides like copper oxychloride or systemic fungicides such as triadimefon, especially during rainy seasons. Regularly monitor for early signs on new growth."
                                        "Brown Blight" -> "Cultural: Prune infected leaves and maintain good field hygiene to limit the spread.\nChemical: Use fungicides like carbendazim or copper-based sprays. Preventive treatments may be applied during humid conditions to reduce infection risks."
                                        else -> "Treatment options for the diseases"
                                    },   fontSize = 14.sp,
                                    color = Color.Gray
                                )
                                }
                                else{
                                    showAlertDialog(onDismiss = { showDialog.value = false })
                                }
                            }
                        }
                    }
                }
        }
    }
}
suspend fun uploadImage(imageBytes: ByteArray): String {
    val client = HttpClient(CIO) {
        install(Logging) {
            level = LogLevel.INFO}
            engine {
                endpoint {
                    connectTimeout = 60000
                    requestTimeout = 60000
                    socketTimeout = 60000
                }
        }
    }

    return try {
        val response: HttpResponse = client.submitFormWithBinaryData(
            url = "https://us-central1-tea-factory-management-system.cloudfunctions.net/teadiseasedetection",
            formData = formData {
                append("image", imageBytes, Headers.build {
                    append(HttpHeaders.ContentType, "image/jpeg")
                    append(HttpHeaders.ContentDisposition, "filename=\"uploaded_image.jpg\"")
                })
            }
        )


        val responseBody = response.bodyAsText()

        if (response.status == HttpStatusCode.OK) {
            responseBody
        } else {
            "Error: ${response.status} - ${responseBody}"
        }
    } catch (e: Exception) {
        "Failed to upload image: ${e.message}"
    } finally {
        client.close()
    }
}
@Composable
fun showAlertDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Invalid image") },
        text = {
            Column {
                Text(text = "Please upload a valid image.")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "This image can't be identified by the AI model. Please upload a clear and valid image and try again.")
            }
        },
        confirmButton = {  },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = "Close", color = Color.Green)
            }
        }
    )
}
