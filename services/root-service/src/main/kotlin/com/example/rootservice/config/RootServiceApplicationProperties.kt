package com.example.rootservice.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
data class RootServiceApplicationProperties(
    val calculation: Calculation,
    val xorService: XorService,
    val maxRandomValue: Long
) {

    data class Calculation(
        val cpsLimit: Double
    )

    data class XorService (
        val url: String
    )
}
