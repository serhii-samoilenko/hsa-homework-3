package com.example.xorservice.api

import com.example.xorservice.api.dto.XorResponse
import com.example.xorservice.service.XorService
import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(produces = ["application/json"])
class XorController(private val xorService: XorService) {

    @Timed(value = "time", extraTags = ["endpoint", "/xor/{base}/{modifier}"])
    @Counted(value = "count", extraTags = ["endpoint", "/xor/{base}/{modifier}"])
    @GetMapping("/xor/{base}/{modifier}")
    fun getXorResult(@PathVariable base: Long, @PathVariable modifier: Long): XorResponse {
        val result = xorService.getXorResult(base, modifier)
        return XorResponse(
            base = base,
            modifier = modifier,
            result = result
        )
    }

    companion object : KLogging()
}
