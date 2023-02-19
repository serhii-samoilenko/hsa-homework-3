package com.example.geoservice.client

import com.example.geoservice.client.dto.LocationResponse
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
import java.math.BigDecimal

interface WeatherApiClient {

    @RequestLine("GET /search.json?key={apiKey}&q={lat},{lon}")
    @Headers("Content-Type: application/json")
    fun searchCitiesByLocation(
        @Param("apiKey") apiKey: String,
        @Param("lat") lat: BigDecimal,
        @Param("lon") lon: BigDecimal
    ): List<LocationResponse>
}

@Configuration
class WeatherApiClientConfiguration(
    private val properties: GeoServiceApplicationProperties
) {

    @Bean
    fun weatherApiClient(): WeatherApiClient = Feign.builder()
        .decoder(JacksonDecoder(ObjectMapper().registerModule(KotlinModule.Builder().build())))
        .target(WeatherApiClient::class.java, properties.weatherApi.url)
}
