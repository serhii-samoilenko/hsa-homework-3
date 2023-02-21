package com.example.rootservice.service

import com.example.rootservice.persistence.CacheRepository
import com.example.rootservice.persistence.entity.RootCacheEntity
import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import org.springframework.stereotype.Service

@Service
class PersistenceService(private val cacheRepository: CacheRepository) {

    @Timed(value = "time", extraTags = ["domain", "persistence", "method", "lookupCache"])
    @Counted(value = "count", extraTags = ["operation", "lookupCache"])
    fun lookupCache(value: Long): RootCacheEntity? = cacheRepository.findByValue(value)

    @Timed(value = "time", extraTags = ["domain", "persistence", "method", "saveToCache"])
    @Counted(value = "count", extraTags = ["operation", "saveToCache"])
    fun saveToCache(rootCacheEntity: RootCacheEntity): RootCacheEntity = cacheRepository.save(rootCacheEntity)

    @Counted(value = "count", extraTags = ["operation", "dropData"])
    fun dropData() = cacheRepository.deleteAll()
}
