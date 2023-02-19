package com.example.weatherservice.client

import com.example.weatherservice.client.dto.CurrentWeatherResponse
import com.example.weatherservice.config.WeatherServiceApplicationProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import feign.Feign
import feign.Headers
import feign.Param
import feign.RequestLine
import feign.jackson.JacksonDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

interface WeatherApiClient {

    @RequestLine("GET /current.json?key={apiKey}&q={location}")
    @Headers("Content-Type: application/json")
    fun getCurrentWeather(@Param("apiKey") apiKey: String, @Param("location") location: String): CurrentWeatherResponse
}

@Configuration
class WeatherApiClientConfiguration(
    private val properties: WeatherServiceApplicationProperties
) {

    @Bean
    fun weatherApiClient(): WeatherApiClient = Feign.builder()
        .decoder(JacksonDecoder(ObjectMapper().registerModule(KotlinModule.Builder().build())))
        .target(WeatherApiClient::class.java, properties.weatherApi.url)
}
