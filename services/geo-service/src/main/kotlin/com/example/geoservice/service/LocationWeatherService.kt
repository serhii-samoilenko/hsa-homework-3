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

    @Timed(value = "time", extraTags = ["domain", "general", "method", "getCityWeather"])
    @Counted(value = "count", extraTags = ["operation", "getCityWeather"])
    fun getCitesWeatherByCoordinates(lat: BigDecimal, lon: BigDecimal): List<CityWeatherResponse> {
        logger.debug { "Fetching cities for coordinates: $lat,$lon" }
        var locationDocument = persistenceService.findLocationByCoordinates(lat, lon)
        logger.debug { "Found location document: $locationDocument" }
        if (locationDocument == null) {
            val cities = weatherApiService.fetchCitiesByCoordinates(lat, lon)
            logger.debug { "Found cities via API: $cities" }
            locationDocument = LocationDocument("$lat:$lon", cities.map { it.name })
            val cityLocationDocuments = cities.map { city ->
                LocationDocument("${city.lat}:${city.lon}", listOf(city.name))
            }
            val documents = persistenceService.saveAll(listOf(locationDocument) + cityLocationDocuments)
            logger.debug { "Saved ${documents.count()} documents" }
        }
        val cityWeatherList = locationDocument.cities.parallelStream().map { cityName ->
            weatherServiceClient.getCityWeather(cityName)
        }.toList()
        logger.debug { "Returning city weather list: $cityWeatherList" }
        return cityWeatherList
    }

    companion object : KLogging()
}
