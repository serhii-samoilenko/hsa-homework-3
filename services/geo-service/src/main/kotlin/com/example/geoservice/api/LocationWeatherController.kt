package com.example.geoservice.api

import com.example.geoservice.client.dto.CityWeatherResponse
import com.example.geoservice.service.LocationWeatherService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

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
        val lat = BigDecimal.valueOf(Math.random() * (bounds["north"]!! - bounds["south"]!!) + bounds["south"]!!)
        val lon = BigDecimal.valueOf(Math.random() * (bounds["east"]!! - bounds["west"]!!) + bounds["west"]!!)
        return locationWeatherService.getCitesWeatherByCoordinates(lat, lon)
    }

    companion object {
        val bounds = mapOf(
            "north" to 71.18,
            "south" to 35.03,
            "west" to -11.59,
            "east" to 41.56
        )
    }
}
