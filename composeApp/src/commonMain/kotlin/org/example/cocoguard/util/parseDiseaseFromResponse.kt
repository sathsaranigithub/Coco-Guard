package org.example.cocoguard.util

import kotlinx.serialization.json.Json
import org.example.cocoguard.view.disease.DetectionResult

fun parseDiseaseFromResponse(response: String): String {
    return try {
        val detectionResult = Json.decodeFromString<DetectionResult>(response)
        detectionResult.`class`
    } catch (e: Exception) {
        "Unknown disease"
    }
}