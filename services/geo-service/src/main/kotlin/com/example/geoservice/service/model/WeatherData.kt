package com.example.geoservice.service.model

import java.math.BigDecimal

data class City(
    val name: String,
    val lat: BigDecimal,
    val lon: BigDecimal
)
