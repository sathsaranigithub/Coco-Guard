package org.example.cocoguard.controller

import androidx.navigation.NavController
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.example.cocoguard.model.Treatment

class Diseases {
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
                url = "https://asia-south1-plucky-pointer-443915-u7.cloudfunctions.net/coconut-diseases",
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
    // API call to Gemini API
    @Serializable
    data class GeminiResponse(val candidates: List<Candidate>)
    @Serializable
    data class Candidate(val content: Content)
    @Serializable
    data class Content(val parts: List<Part>)
    @Serializable
    data class Part(val text: String)
    suspend fun callGeminiAPI(queryText: String, email: String, repository: FirestoreRepository, navController: NavController): String {
        return withContext(Dispatchers.IO) {
            val client = HttpClient(CIO) {
                // Optional configuration
            }
            try {
                val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=AIzaSyCwzZzbkmlgookWjngwVh8BHQSVP8HPEyk"
                val requestBody = """
                {
                    "contents": [
                        {
                            "parts": [
                                {
                                    "text": "$queryText"
                                }
                            ]
                        }
                    ]
                }
            """
                val response: HttpResponse = client.post(url) {
                    headers { append(HttpHeaders.ContentType, ContentType.Application.Json.toString()) }
                    setBody(requestBody)
                }
                val responseText = response.bodyAsText()
                println("GeminiAPIResponse: $responseText") // Debug log
                // Parse the JSON response
                val json = Json { ignoreUnknownKeys = true }
                val geminiResponse = json.decodeFromString<GeminiResponse>(responseText)
                // Extract the text content
                val textContent = geminiResponse.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
                textContent?.also { println("Extracted Text: $it") }
                // If text is extracted, save it to Firebase
                if (textContent != null) {
                    val treatment = Treatment(text = textContent)
                    repository.addTreatment(email, treatment) // Save using the repository
                    println("Saved treatment to Firestore for email: $email")
                    // Navigate to diseaseTreatmentScreen
                    withContext(Dispatchers.Main) {
                        navController.navigate("diseaseTreatmentScreen/$email")
                    }
                    return@withContext "Text saved to Firebase: $textContent"
                } else {
                    println("No text found in API response")
                    return@withContext "No text found in API response"
                }
            } catch (e: Exception) {
                println("GeminiAPIError: ${e.localizedMessage}") // Debug error log
                return@withContext "Error: ${e.localizedMessage}"
            } finally {
                client.close() // Ensure the client is closed
            }
        }
    }

}
