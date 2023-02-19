package com.example.weatherservice.persistence.entity

import org.springframework.data.annotation.Id

data class CityWeather(
    @Id
    val city: String,
    val lat: Double,
    val lon: Double,
    val temperature: Double
)
