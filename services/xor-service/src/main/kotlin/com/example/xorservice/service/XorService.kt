package com.example.xorservice.service

import com.example.xorservice.persistence.entity.CacheDocument
import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import io.micrometer.core.instrument.MeterRegistry
import mu.KLogging
import org.springframework.stereotype.Service

@Service
@Suppress("UnstableApiUsage")
class XorService(
    private val persistenceService: PersistenceService,
    private val calculationService: CalculationService,
    meterRegistry: MeterRegistry
) {
    private val hitsCounter = meterRegistry.counter("count", "cache", "hits")
    private val misesCounter = meterRegistry.counter("count", "cache", "mises")

    @Timed(value = "time", extraTags = ["domain", "general", "method", "getXorResult"])
    @Counted(value = "count", extraTags = ["operation", "getXorResult"])
    fun getXorResult(base: Long, modifier: Long): Long {
        logger.debug { "Looking up cached result for base: $base and modifier: $modifier" }
        val cache = persistenceService.lookupCache(base, modifier)
        if (cache != null) {
            logger.debug { "Cache hit, returning cached value" }
            hitsCounter.increment()
            return cache.results.toMap()[modifier] ?: throw IllegalStateException(
                "Cache with bounds ${cache.modifierLowerBound} - ${cache.modifierUpperBound} does not contain value for $modifier"
            )
        }
        logger.debug { "Cache miss, calculating new value" }
        misesCounter.increment()
        val pairs = calculationService.calculateXor(base, modifier)
        val lowerBound = pairs.first().first
        val upperBound = pairs.last().first
        val newCache = CacheDocument(
            base = base,
            modifierLowerBound = lowerBound,
            modifierUpperBound = upperBound,
            results = pairs
        )
        persistenceService.saveToCache(newCache)
        logger.debug { "Cache updated" }
        return newCache.results.toMap()[modifier] ?: throw IllegalStateException(
            "New Cache with bounds ${newCache.modifierLowerBound} - ${newCache.modifierUpperBound} does not contain value $modifier"
        )
    }

    companion object : KLogging()
}
