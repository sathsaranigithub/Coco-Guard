package org.example.cocoguard.controller

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.cocoguard.model.DemandPredictionRequest
import org.example.cocoguard.view.forecasting.DemandPredictionResponse

class Forecasting {
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
}
