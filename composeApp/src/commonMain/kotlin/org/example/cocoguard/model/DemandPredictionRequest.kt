package org.example.cocoguard.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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