package org.example.cocoguard.controller

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.example.cocoguard.model.YieldPredictionRequest
import org.example.cocoguard.view.yield.YieldPredictionResponse


class YieldFeature {

    suspend fun fetchYieldPrediction(client: HttpClient, request: YieldPredictionRequest): YieldPredictionResponse? {
        return try {
            client.post("https://asia-south1-plucky-pointer-443915-u7.cloudfunctions.net/coconut-yieldprediction") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body() // Deserialize directly into YieldPredictionResponse
        } catch (e: Exception) {
            println("Error occurred: ${e.message}")
            null
        }
    }


}
