package com.example.weatherservice.service

import com.example.weatherservice.client.WeatherApiClient
import com.example.weatherservice.config.WeatherServiceApplicationProperties
import com.example.weatherservice.service.model.WeatherData
import com.google.common.util.concurrent.RateLimiter
import feign.FeignException
import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import org.springframework.stereotype.Service

@Service
@Suppress("UnstableApiUsage")
class WeatherApiService(
    private val weatherApiClient: WeatherApiClient,
    properties: WeatherServiceApplicationProperties
) {
    // This API is too fast, rate limit it to 100 requests per second to see some latency
    private val rateLimiter = RateLimiter.create(100.0)
    private val apiKey: String = properties.weatherApi.key

    @Timed(value = "time", extraTags = ["domain", "external", "method", "fetchWeather"])
    @Counted(value = "count", extraTags = ["operation", "fetchWeather"])
    fun fetchWeather(city: String): WeatherData? = try {
        rateLimiter.acquire()
        weatherApiClient.getCurrentWeather(apiKey, city).let {
            WeatherData(it.current.temp_c)
        }
    } catch (e: FeignException.BadRequest) {
        null
    }
}
