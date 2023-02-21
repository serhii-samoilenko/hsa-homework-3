package com.example.xorservice.service

import com.example.xorservice.persistence.XorCacheRepository
import com.example.xorservice.persistence.entity.CacheDocument
import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import mu.KLogging
import org.springframework.data.elasticsearch.NoSuchIndexException
import org.springframework.stereotype.Service

@Service
class PersistenceService(private val xorCacheRepository: XorCacheRepository) {

    @Timed(value = "time", extraTags = ["domain", "persistence", "method", "lookupCache"])
    @Counted(value = "count", extraTags = ["operation", "lookupCache"])
    fun lookupCache(base: Long, modifier: Long): CacheDocument? = try {
        // TODO: This is not a good way to do this, but it works for now
        xorCacheRepository
            .findAllByBaseAndModifierLowerBoundLessThanEqualAndModifierUpperBoundGreaterThanEqual(base, modifier, modifier)
            .firstOrNull()
    } catch (e: NoSuchIndexException) {
        logger.warn { "Index not found, returning null" }
        null
    }

    @Timed(value = "time", extraTags = ["domain", "persistence", "method", "saveToCache"])
    @Counted(value = "count", extraTags = ["operation", "save"])
    fun saveToCache(cacheDocument: CacheDocument) {
        xorCacheRepository.save(cacheDocument)
    }

    @Counted(value = "count", extraTags = ["operation", "dropCache"])
    fun dropCache() {
        xorCacheRepository.deleteAll()
    }

    companion object : KLogging()
}
