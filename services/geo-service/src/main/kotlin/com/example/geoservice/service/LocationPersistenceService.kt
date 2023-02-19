package com.example.geoservice.service

import com.example.geoservice.persistence.LocationRepository
import com.example.geoservice.persistence.entity.LocationDocument
import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import mu.KLogging
import org.springframework.data.elasticsearch.NoSuchIndexException
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class LocationPersistenceService(private val locationRepository: LocationRepository) {

    @Timed(value = "time", extraTags = ["domain", "persistence", "method", "findLocationByCoordinates"])
    @Counted(value = "count", extraTags = ["operation", "findLocationByCoordinates"])
    fun findLocationByCoordinates(lat: BigDecimal, lon: BigDecimal): LocationDocument? = try {
        locationRepository.findByCoordinates("$lat:$lon")
    } catch (e: NoSuchIndexException) {
        logger.warn { "Index not found, returning null" }
        null
    }

    @Timed(value = "time", extraTags = ["domain", "persistence", "method", "saveAll"])
    @Counted(value = "count", extraTags = ["operation", "saveAll"])
    fun saveAll(locationDocuments: List<LocationDocument>): Iterable<LocationDocument> =
        locationRepository.saveAll(locationDocuments)

    @Timed(value = "time", extraTags = ["domain", "persistence", "method", "save"])
    @Counted(value = "count", extraTags = ["operation", "save"])
    fun save(locationDocument: LocationDocument) {
        locationRepository.save(locationDocument)
    }

    @Counted(value = "count", extraTags = ["operation", "dropLocationData"])
    fun dropLocationData() {
        locationRepository.deleteAll()
    }

    companion object : KLogging()
}
