package org.example.cocoguard.utils

fun validateInputs(
    month: String,
    region: String,
    coconutExportVolume: String,
    domesticCoconutConsumption: String,
    coconutPriceLocal: String,
    internationalCoconutPrice: String,
    exportDestinationDemand: String,
    currencyExchangeRate: String,
    competitorCountriesProduction: String
): Pair<Boolean, String> {
    return when {
        month.isBlank() -> Pair(false, "Month is required.")
        region.isBlank() -> Pair(false, "Region is required.")
        coconutExportVolume.isBlank() -> Pair(false, "Coconut export volume is required.")
        domesticCoconutConsumption.isBlank() -> Pair(false, "Domestic coconut consumption is required.")
        coconutPriceLocal.isBlank() -> Pair(false, "Local coconut price is required.")
        internationalCoconutPrice.isBlank() -> Pair(false, "International coconut price is required.")
        exportDestinationDemand.isBlank() -> Pair(false, "Export destination demand is required.")
        currencyExchangeRate.isBlank() -> Pair(false, "Currency exchange rate is required.")
        competitorCountriesProduction.isBlank() -> Pair(false, "Competitor countries' production is required.")
        else -> Pair(true, "")
    }
}

fun validateInputsyield(
    soilPH: String,
    humidity: String,
    temperature: String,
    sunlightHours: String,
    plantAge: String
): Pair<Boolean, String> {
    return when {
        soilPH.isBlank() -> {
            false to "Soil pH is required."
        }

        soilPH.toDoubleOrNull() == null -> {
            false to "Soil pH must be a valid number."
        }

        humidity.isBlank() -> {
            false to "Humidity percentage is required."
        }

        humidity.toIntOrNull() == null -> {
            false to "Humidity must be a valid number."
        }

        temperature.isBlank() -> {
            false to "Temperature is required."
        }

        temperature.toIntOrNull() == null -> {
            false to "Temperature must be a valid number."
        }

        sunlightHours.isBlank() -> {
            false to "Sunlight hours are required."
        }

        sunlightHours.toIntOrNull() == null -> {
            false to "Sunlight hours must be a valid number."
        }

        plantAge.isBlank() -> {
            false to "Plant age is required."
        }

        plantAge.toIntOrNull() == null -> {
            false to "Plant age must be a valid number."
        }

        else -> {
            true to ""
        }
    }
}