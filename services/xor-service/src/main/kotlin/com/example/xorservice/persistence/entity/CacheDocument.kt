package com.example.xorservice.persistence.entity

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import java.util.UUID

@Document(indexName = "xor-cache")
data class CacheDocument(
    @Id
    val id: String = UUID.randomUUID()!!.toString(),
    val base: Long,
    val modifierLowerBound: Long,
    val modifierUpperBound: Long,
    val results: List<Pair<Long, Long>>
)
