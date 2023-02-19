package com.example.geoservice.service

import com.example.geoservice.client.WeatherServiceClient
import com.example.geoservice.client.dto.CityWeatherResponse
import com.example.geoservice.persistence.entity.LocationDocument
import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import mu.KLogging
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class LocationWeatherService(
    private val persistenceService: LocationPersistenceService,
    private val weatherApiService: WeatherApiService,
    private val weatherServiceClient: WeatherServiceClient
) {

    @Timed(value = "time", extraTags = ["domain", "general", "method", "getCitesWeatherByCoordinates"])
    @Counted(value = "count", extraTags = ["operation", "getCitesWeatherByCoordinates"])
    fun getCitesWeatherByCoordinates(lat: BigDecimal, lon: BigDecimal): List<CityWeatherResponse> {
        logger.debug { "Fetching cities for coordinates: $lat,$lon" }
        var locationDocument = persistenceService.findLocationByCoordinates(lat, lon)
        logger.debug { "Found location document: $locationDocument" }
        if (locationDocument == null) {
            val city = weatherApiService.fetchCitiesByCoordinates(lat, lon).first()
            logger.debug { "Found city via API: $city" }
            locationDocument = LocationDocument("$lat:$lon", listOf(city.name))
            val cityLocationDocument = LocationDocument("${city.lat}:${city.lon}", listOf(city.name))
            val documents = persistenceService.saveAll(listOf(locationDocument, cityLocationDocument))
            logger.debug { "Saved documents: $documents" }
        }
        val cityWeatherList = locationDocument.cities.parallelStream().map { cityName ->
            weatherServiceClient.getCityWeather(cityName)
        }.toList()
        logger.debug { "Returning city weather list: $cityWeatherList" }
        return cityWeatherList
    }

    companion object : KLogging()
}
