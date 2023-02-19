package com.example.geoservice.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration

@Configuration
class ElasticsearchConfig(
    private val properties: GeoServiceApplicationProperties
) : ElasticsearchConfiguration() {
    override fun clientConfiguration(): ClientConfiguration {
        return ClientConfiguration.builder()
            .connectedTo(properties.elasticsearch.url)
            .build()
    }
}

