package com.example.geoservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.util.Base64

@ConfigurationProperties(prefix = "app")
data class GeoServiceApplicationProperties(
    val weatherApi: WeatherApi,
    val weatherService: WeatherService
) {

    data class WeatherApi(
        val obfuscatedKey: String,
        val url: String
    ) {
        val key: String
            get() = lazy { Base64.getDecoder().decode(obfuscatedKey).decodeToString().trim() }.value
    }

    data class WeatherService(
        val url: String
    )
}
