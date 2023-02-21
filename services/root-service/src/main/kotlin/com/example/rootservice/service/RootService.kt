package com.example.rootservice.service

import com.example.rootservice.persistence.entity.RootCacheEntity
import io.micrometer.core.instrument.MeterRegistry
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class RootService(
    private val persistenceService: PersistenceService,
    private val xorApiService: XorApiService,
    private val calculationService: CalculationService,
    meterRegistry: MeterRegistry
) {
    private val hitsCounter = meterRegistry.counter("count", "cache", "hits")
    private val misesCounter = meterRegistry.counter("count", "cache", "mises")

    fun getRootOfXor(base: Long, modifier: Long): RootCacheEntity {
        logger.debug { "Fetching xor result for $base and $modifier" }
        val xorResult = xorApiService.fetchXorResult(base, modifier)
        logger.debug { "Looking up cached result for xor result: $xorResult" }
        val cache = persistenceService.lookupCache(xorResult)
        if (cache != null) {
            logger.debug { "Cache hit, returning cached value" }
            hitsCounter.increment()
            return cache
        }
        logger.debug { "Cache miss, calculating new value" }
        misesCounter.increment()
        val result = calculationService.calculateRoot(xorResult)
        logger.debug { "Calculated root: $result" }
        val newCache = RootCacheEntity(xorResult, result)
        val document = persistenceService.saveToCache(newCache)
        logger.debug { "Saved document: $document" }
        return document
    }

    companion object : KLogging()
}
