package org.example.cocoguard.model

import kotlinx.serialization.Serializable

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