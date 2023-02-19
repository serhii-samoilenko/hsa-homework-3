package com.example.geoservice.persistence

import com.example.geoservice.persistence.entity.LocationDocument
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository : ElasticsearchRepository<LocationDocument, String> {
    fun findByCoordinates(coordinates: String): LocationDocument?
}
