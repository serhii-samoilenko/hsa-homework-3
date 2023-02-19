package com.example.geoservice

import com.example.geoservice.config.GeoServiceApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableConfigurationProperties(GeoServiceApplicationProperties::class)
class GeoServiceApplication

fun main(args: Array<String>) {
    runApplication<GeoServiceApplication>(*args)
}
