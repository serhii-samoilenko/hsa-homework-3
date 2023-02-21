package com.example.xorservice.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
data class XorServiceApplicationProperties(
    val elasticsearch: Elasticsearch,
    val calculation: Calculation
) {

    data class Calculation(
        val cpsLimit: Double,
        val cacheSize: Int
    )

    data class Elasticsearch(
        val url: String
    )
}
