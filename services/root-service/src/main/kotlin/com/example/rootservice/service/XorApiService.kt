package com.example.rootservice.service

import com.example.rootservice.client.XorServiceClient
import io.micrometer.core.annotation.Counted
import io.micrometer.core.annotation.Timed
import org.springframework.stereotype.Service

@Service
class XorApiService(
    private val xorServiceClient: XorServiceClient
) {

    @Timed(value = "time", extraTags = ["method", "fetchXorResult"])
    @Counted(value = "count", extraTags = ["method", "fetchXorResult"])
    fun fetchXorResult(base: Long, modifier: Long): Long =
        xorServiceClient.getXorResult(base, modifier).result
}
