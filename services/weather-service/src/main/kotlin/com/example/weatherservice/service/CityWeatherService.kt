package com.example.weatherservice.service

import com.example.weatherservice.persistence.entity.CityWeather
import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import mu.KLogging
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
            logger.debug { "City weather not found in persistence, fetching from API: $city" }
            val weather = weatherApiService.fetchWeather(city)
            logger.debug { "Fetched weather: $weather" }
            if (weather == null) {
                logger.warn { "Weather not found for city: $city" }
                null
            } else {
                val cityWeather = CityWeather(city, weather.lat, weather.lon, weather.temperature)
                logger.debug { "Saving city weather: $cityWeather" }
                persistenceService.putCityWeather(cityWeather)
            }
        }

    companion object : KLogging()
}
