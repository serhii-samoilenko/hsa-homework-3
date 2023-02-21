package com.example.xorservice

import com.example.xorservice.config.XorServiceApplicationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableConfigurationProperties(XorServiceApplicationProperties::class)
class XorServiceApplication

fun main(args: Array<String>) {
    runApplication<XorServiceApplication>(*args)
}
