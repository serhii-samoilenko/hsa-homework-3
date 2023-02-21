package com.example.rootservice.persistence

import com.example.rootservice.persistence.entity.RootCacheEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CacheRepository : MongoRepository<RootCacheEntity, String> {
    fun findByValue(value: Long): RootCacheEntity?
}
