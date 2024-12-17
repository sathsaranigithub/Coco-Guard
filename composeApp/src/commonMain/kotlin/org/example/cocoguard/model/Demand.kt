package org.example.cocoguard.model

data class Demand(
    var month: String = "",
    var region: String = "",
    var coconutExportVolume: String = "",
    var domesticCoconutConsumption: String = "",
    var coconutPriceLocal: String = "",
    var internationalCoconutPrice: String = "",
    var exportDestinationDemand: String = "",
    var currencyExchangeRate: String = "",
    var competitorCountriesProduction: String = "",
    var predictionResult: String = "",
)
