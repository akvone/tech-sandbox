package com.akvone.dynamic_properties

import com.akvone.dynamic_properties.internal.DynamicProperty
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@Component
@EnableConfigurationProperties(DynamicProperties::class)
class DynamicRunner(
    private val dynamicProperties: DynamicProperties,
    @Value("\${feature.dynamic-properties.baseCurrency}") val baseCurrency: DynamicProperty<String>,
) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(DynamicRunner::class.java)

    override fun run(args: ApplicationArguments?) {
        log.info("Dynamic property by @ConfigurationProperties: ${dynamicProperties.baseCurrency.get()}")
        log.info("Dynamic property by @Value: ${baseCurrency.get()}")
    }
}