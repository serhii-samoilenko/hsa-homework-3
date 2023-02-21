package com.example.xorservice.persistence

import com.example.xorservice.persistence.entity.CacheDocument
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface XorCacheRepository : ElasticsearchRepository<CacheDocument, String> {

    fun findAllByBaseAndModifierLowerBoundLessThanEqualAndModifierUpperBoundGreaterThanEqual(
        base: Long,
        lowerBound: Long,
        upperBound: Long
    ): List<CacheDocument>
}
