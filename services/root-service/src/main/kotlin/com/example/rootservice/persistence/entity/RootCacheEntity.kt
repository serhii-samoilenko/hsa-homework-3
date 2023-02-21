package com.example.rootservice.persistence.entity

import org.springframework.data.annotation.Id

data class RootCacheEntity(
    @Id
    val value: Long,
    val result: Long
)
