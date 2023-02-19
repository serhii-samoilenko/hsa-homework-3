package com.example.geoservice.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "location")
data class LocationDocument(
    @Id
    val coordinates: String,
    val cities: List<String>
)
