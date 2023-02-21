package com.example.rootservice.client

import com.example.rootservice.client.dto.XorResponse
import com.example.rootservice.config.RootServiceApplicationProperties
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import feign.Feign
import feign.Headers
import feign.Param
import feign.RequestLine
import feign.jackson.JacksonDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

interface XorServiceClient {

    @RequestLine("GET /xor/{base}/{modifier}")
    @Headers("Content-Type: application/json")
    fun getXorResult(@Param("base") base: Long, @Param("modifier") modifier: Long): XorResponse
}

@Configuration
class WeatherApiClientConfiguration(
    private val properties: RootServiceApplicationProperties
) {

    @Bean
    fun weatherApiClient(): XorServiceClient = Feign.builder()
        .decoder(JacksonDecoder(ObjectMapper().registerModule(KotlinModule.Builder().build())))
        .target(XorServiceClient::class.java, properties.xorService.url)
}
