package com.example.rootservice.service

import com.example.rootservice.config.RootServiceApplicationProperties
import com.google.common.util.concurrent.RateLimiter
import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import mu.KLogging
import org.springframework.stereotype.Service
import kotlin.math.sqrt

@Service
@Suppress("UnstableApiUsage")
class CalculationService(
    properties: RootServiceApplicationProperties
) {
    // To emulate expensive operation, limiting total rate
    private val rateLimiter = RateLimiter.create(properties.calculation.cpsLimit)

    @Timed(value = "time", extraTags = ["method", "calculateRoot"])
    @Counted(value = "count", extraTags = ["method", "calculateRoot"])
    fun calculateRoot(value: Long): Long {
        rateLimiter.acquire()
        return sqrt(value.toDouble()).toLong()
    }

    companion object : KLogging()
}
