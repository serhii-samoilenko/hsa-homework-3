package com.example.weatherservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.util.Base64

@ConfigurationProperties(prefix = "app")
data class WeatherServiceApplicationProperties(
    val weatherApi: WeatherApi
) {

    data class WeatherApi(
        val obfuscatedKey: String,
        val url: String,
        val rpsLimit: Double
    ) {
        val key: String
            get() = lazy { Base64.getDecoder().decode(obfuscatedKey).decodeToString().trim() }.value
    }
}
