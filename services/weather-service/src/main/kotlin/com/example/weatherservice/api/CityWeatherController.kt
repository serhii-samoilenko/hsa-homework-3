package com.example.weatherservice.api

import com.example.weatherservice.service.CityWeatherService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/weather"], produces = ["application/json"])
class CityWeatherController(
    private val cityWeatherService: CityWeatherService
) {

    @GetMapping("/city/{city}")
    fun getCityWeather(@PathVariable city: String) =
        cityWeatherService.getCityWeather(city)
            ?: throw ResourceNotFoundException("City $city not found")

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    class ResourceNotFoundException(msg: String) : RuntimeException(msg)
}
