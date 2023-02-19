package com.example.geoservice.client.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.math.BigDecimal

@JsonIgnoreProperties(ignoreUnknown = true)
data class LocationResponse(
    val id: Int,
    val name: String,
    val region: String,
    val country: String,
    val lat: BigDecimal,
    val lon: BigDecimal,
    val url: String
)
