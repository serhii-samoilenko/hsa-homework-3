package com.example.weatherservice.config

import io.micrometer.core.aop.CountedAspect
import io.micrometer.core.aop.TimedAspect
import io.micrometer.core.instrument.MeterRegistry
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component


@Configuration
class AutoTimingConfiguration {
    @Bean
    fun timedAspect(registry: MeterRegistry) = TimedAspect(registry)

    @Bean
    fun countedAspect(registry: MeterRegistry) = CountedAspect(registry)
}

@Component
class MeterRegistryBeanPostProcessor(
    @Value("\${spring.application.name}") private val applicationName: String
) : BeanPostProcessor {
    override fun postProcessAfterInitialization(bean: Any, beanName: String) = bean.also {
        if (it is MeterRegistry) {
            logger.info { "Adding common tags to meter registry" }
            it.config().commonTags("application", applicationName)
        }
    }

    companion object : KLogging()
}
