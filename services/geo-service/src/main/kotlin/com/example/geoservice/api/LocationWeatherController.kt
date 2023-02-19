package com.example.geoservice.api

import com.example.geoservice.client.dto.CityWeatherResponse
import com.example.geoservice.service.LocationWeatherService
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.Math.random
import java.math.BigDecimal
import java.math.RoundingMode.FLOOR

@RestController
@RequestMapping(value = ["/weather"], produces = ["application/json"])
class LocationWeatherController(
    private val locationWeatherService: LocationWeatherService
) {

    @GetMapping("/location/{lat}:{lon}")
    fun getCitesWeatherByCoordinates(@PathVariable lat: BigDecimal, @PathVariable lon: BigDecimal) =
        locationWeatherService.getCitesWeatherByCoordinates(lat, lon)

    @GetMapping("/location/random")
    fun getCitesWeatherByRandomCoordinates(): List<CityWeatherResponse> {
        val lat = BigDecimal.valueOf(random() * (north - south) + south).setScale(2, FLOOR)
        val lon = BigDecimal.valueOf(random() * (east - west) + west).setScale(2, FLOOR)
        logger.info { "Random coordinates: $lat:$lon" }
        return locationWeatherService.getCitesWeatherByCoordinates(lat, lon)
    }

    companion object : KLogging() {
        const val north = 71.185476
        const val south = 35.029996
        const val west = 0.0 // OMG the API doesn't support negative values
        const val east = 41.563271
    }
}
