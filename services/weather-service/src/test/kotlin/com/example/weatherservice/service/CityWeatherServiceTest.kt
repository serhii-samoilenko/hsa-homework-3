package com.example.weatherservice.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

private const val CITY = "London"
private const val TEMPERATURE = 10.0

@SpringBootTest
class CityWeatherServiceTest {

    @Autowired
    private lateinit var cityWeatherServiceImpl: CityWeatherServiceImpl

    @Test
    fun getCityWeather() {
        // Given
        cityWeatherServiceImpl.putCityTemperature(CITY, TEMPERATURE)
        // When
        val actual = cityWeatherServiceImpl.getCityWeather(CITY)
        // Then
        assertNotNull(actual)
        assertEquals(TEMPERATURE, actual!!.temperature)
    }

    @Test
    fun `fetch weather for existing city`() {
        // When
        val actual = cityWeatherServiceImpl.fetchWeather(CITY)
        // Then
        assertNotNull(actual)
        assertNotNull(actual!!.temperature)
    }

    @Test
    fun `fetch weather for non-existing city`() {
        // When
        val actual = cityWeatherServiceImpl.fetchWeather("ioewiuerbireiael")
        // Then
        assertNotNull(actual)
        assertNotNull(actual!!.temperature)
    }
}
