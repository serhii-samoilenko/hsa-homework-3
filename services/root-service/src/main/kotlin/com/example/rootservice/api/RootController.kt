package com.example.rootservice.api

import com.example.rootservice.config.RootServiceApplicationProperties
import com.example.rootservice.persistence.entity.RootCacheEntity
import com.example.rootservice.service.RootService
import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
@RequestMapping(produces = ["application/json"])
class RootController(
    private val rootService: RootService,
    private val properties: RootServiceApplicationProperties
) {

    @Timed(value = "time", extraTags = ["endpoint", "/root/xor/{base}/{modifier}"])
    @Counted(value = "count", extraTags = ["endpoint", "/root/xor/{base}/{modifier}"])
    @GetMapping("/root/xor/{base}/{modifier}")
    fun getRootOfXor(@PathVariable base: Long, @PathVariable modifier: Long) =
        rootService.getRootOfXor(base, modifier)

    @Timed(value = "time", extraTags = ["endpoint", "/root/xor/random"])
    @Counted(value = "count", extraTags = ["endpoint", "/root/xor/random"])
    @GetMapping("/root/xor/random")
    fun getRootOfXorRandom(): RootCacheEntity {
        val modifier = Random.nextLong(0L, properties.maxRandomValue)
        logger.debug { "Random values: $base, $modifier" }
        return rootService.getRootOfXor(base, modifier)
    }

    companion object : KLogging() {
        val base = Random.nextLong(0L, Long.MAX_VALUE)
    }
}
