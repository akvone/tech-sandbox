package com.akvone.sandbox.metrics

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MetricsConfiguration {

    @Bean
    fun meterRegistryCustomizer(@Value("\${spring.application.name}") applicationName: String): MeterRegistryCustomizer<MeterRegistry> =
        MeterRegistryCustomizer {
            it.config().commonTags(
                listOf(
                    Tag.of("application-name", applicationName)
                )
            )
        }

}