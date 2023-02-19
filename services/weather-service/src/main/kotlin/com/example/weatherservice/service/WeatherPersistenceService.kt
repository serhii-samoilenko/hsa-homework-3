package com.example.weatherservice.service

import com.example.weatherservice.persistence.CityWeatherRepository
import com.example.weatherservice.persistence.entity.CityWeather
import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import org.springframework.stereotype.Service

@Service
class WeatherPersistenceService(private val cityWeatherRepository: CityWeatherRepository) {

    @Timed(value = "time", extraTags = ["domain", "persistence", "method", "putCityTemperature"])
    @Counted(value = "count", extraTags = ["operation", "putCityTemperature"])
    fun putCityWeather(cityWeather: CityWeather): CityWeather = cityWeatherRepository.save(cityWeather)

    @Timed(value = "time", extraTags = ["domain", "persistence", "method", "findByCity"])
    @Counted(value = "count", extraTags = ["operation", "findByCity"])
    fun findByCity(city: String): CityWeather? = cityWeatherRepository.findByCity(city)

    @Counted(value = "count", extraTags = ["operation", "dropWeatherData"])
    fun dropWeatherData() = cityWeatherRepository.deleteAll()
}
