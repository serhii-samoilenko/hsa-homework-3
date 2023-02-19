package com.example.geoservice.client

import com.example.geoservice.client.dto.CityWeatherResponse
import com.example.geoservice.config.GeoServiceApplicationProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import feign.Feign
import feign.Headers
import feign.Param
import feign.RequestLine
import feign.jackson.JacksonDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

interface WeatherServiceClient {

    @RequestLine("GET /weather/city/{city}")
    @Headers("Content-Type: application/json")
    fun getCityWeather(@Param("city") city: String): CityWeatherResponse
}

@Configuration
class WeatherServiceClientConfiguration(
    private val properties: GeoServiceApplicationProperties
) {

    @Bean
    fun weatherServiceClient(): WeatherServiceClient = Feign.builder()
        .decoder(JacksonDecoder(ObjectMapper().registerModule(KotlinModule.Builder().build())))
        .target(WeatherServiceClient::class.java, properties.weatherService.url)
}
