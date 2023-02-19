package com.example.geoservice.service

import com.example.geoservice.client.WeatherApiClient
import com.example.geoservice.config.GeoServiceApplicationProperties
import com.example.geoservice.service.model.City
import com.google.common.util.concurrent.RateLimiter
import feign.FeignException
import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import mu.KLogging
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
@Suppress("UnstableApiUsage")
class WeatherApiService(
    private val weatherApiClient: WeatherApiClient,
    properties: GeoServiceApplicationProperties
) {
    // This API is too fast, rate limit it to 100 requests per second to see some latency
    private val rateLimiter = RateLimiter.create(100.0)
    private val apiKey: String = properties.weatherApi.key

    @Timed(value = "time", extraTags = ["domain", "external", "method", "fetchWeather"])
    @Counted(value = "count", extraTags = ["operation", "fetchWeather"])
    fun fetchCitiesByCoordinates(lat: BigDecimal, lon: BigDecimal): List<City> = try {
        rateLimiter.acquire()
        weatherApiClient.searchCitiesByLocation(apiKey, lat, lon)
            .map { City(it.name, it.lat, it.lon) }
    } catch (e: FeignException.BadRequest) {
        logger.error { "Bad request to weather API by: $lat,$lon: ${e.message}" }
        emptyList()
    }

    companion object : KLogging()
}
