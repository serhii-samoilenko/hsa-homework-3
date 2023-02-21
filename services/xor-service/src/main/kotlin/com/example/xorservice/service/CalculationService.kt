package com.example.xorservice.service

import com.example.xorservice.config.XorServiceApplicationProperties
import com.google.common.util.concurrent.RateLimiter
import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import mu.KLogging
import org.springframework.stereotype.Service
import kotlin.math.min

@Service
@Suppress("UnstableApiUsage")
class CalculationService(
    properties: XorServiceApplicationProperties
) {
    // To emulate expensive operation, limiting total rate
    private val rateLimiter = RateLimiter.create(properties.calculation.cpsLimit)
    private val cacheSize = properties.calculation.cacheSize

    @Timed(value = "time", extraTags = ["domain", "calculation", "method", "calculateXor"])
    @Counted(value = "count", extraTags = ["operation", "calculateXor"])
    fun calculateXor(base: Long, modifier: Long): List<Pair<Long, Long>> {
        rateLimiter.acquire()
        val median = min(modifier, cacheSize.toLong() / 2)
        val lowerBound = modifier - median
        val upperBound = modifier + median - 1
        return (lowerBound..upperBound).map {
            val result = base xor it
            it to result
        }
    }
    companion object : KLogging()
}
