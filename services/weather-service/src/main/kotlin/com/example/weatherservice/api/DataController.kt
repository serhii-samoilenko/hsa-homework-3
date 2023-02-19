package com.example.weatherservice.api

import com.example.weatherservice.service.WeatherPersistenceService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/data"], produces = ["application/json"])
class DataController(
    private val weatherPersistenceService: WeatherPersistenceService
) {

    @GetMapping("/drop")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun dropWeatherData() {
        weatherPersistenceService.dropWeatherData()
    }
}
