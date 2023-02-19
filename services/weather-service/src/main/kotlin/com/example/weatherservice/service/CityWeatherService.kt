package com.example.weatherservice.service

import com.example.weatherservice.persistence.entity.CityWeather
import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import org.springframework.stereotype.Service

@Service
class CityWeatherService(
    private val persistenceService: WeatherPersistenceService,
    private val weatherApiService: WeatherApiService
) {

    @Timed(value = "time", extraTags = ["domain", "general", "method", "getCityWeather"])
    @Counted(value = "count", extraTags = ["operation", "getCityWeather"])
    fun getCityWeather(city: String): CityWeather? =
        persistenceService.findByCity(city) ?: run {
            weatherApiService.fetchWeather(city)?.let {
                persistenceService.putCityTemperature(city, it.temperature)
            }
        }
}
