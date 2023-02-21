package com.example.rootservice

import com.example.rootservice.config.RootServiceApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy


@SpringBootApplication
@EnableAspectJAutoProxy
@EnableConfigurationProperties(RootServiceApplicationProperties::class)
class RootServiceApplication

fun main(args: Array<String>) {
    runApplication<RootServiceApplication>(*args)
}
