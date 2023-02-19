package com.example.weatherservice

import com.example.weatherservice.config.WeatherServiceApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy


@SpringBootApplication
@EnableAspectJAutoProxy
@EnableConfigurationProperties(WeatherServiceApplicationProperties::class)
class WeatherServiceApplication

fun main(args: Array<String>) {
    runApplication<WeatherServiceApplication>(*args)
}
