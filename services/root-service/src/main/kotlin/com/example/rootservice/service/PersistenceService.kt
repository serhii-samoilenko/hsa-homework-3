package com.example.rootservice.service

import com.example.rootservice.persistence.CacheRepository
import com.example.rootservice.persistence.entity.RootCacheEntity
import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import org.springframework.stereotype.Service

@Service
class PersistenceService(private val cacheRepository: CacheRepository) {

    @Timed(value = "time", extraTags = ["cache", "lookup"])
    @Counted(value = "count", extraTags = ["cache", "lookup"])
    fun lookupCache(value: Long): RootCacheEntity? = cacheRepository.findByValue(value)

    @Timed(value = "time", extraTags = ["cache", "update"])
    @Counted(value = "count", extraTags = ["cache", "update"])
    fun saveToCache(rootCacheEntity: RootCacheEntity): RootCacheEntity = cacheRepository.save(rootCacheEntity)

    @Counted(value = "count", extraTags = ["cache", "drop"])
    fun dropData() = cacheRepository.deleteAll()
}
