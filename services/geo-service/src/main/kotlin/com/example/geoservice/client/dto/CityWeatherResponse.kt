package com.example.geoservice.client.dto

data class CityWeatherResponse(
    val city: String,
    val lat: Double,
    val lon: Double,
    val temperature: Double
)
