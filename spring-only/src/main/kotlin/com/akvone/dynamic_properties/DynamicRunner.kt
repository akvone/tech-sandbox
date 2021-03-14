package com.akvone.dynamic_properties

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@Component
@EnableConfigurationProperties(DynamicProperties::class)
class DynamicRunner(
    private val dynamicProperties: DynamicProperties
) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(DynamicRunner::class.java)

    override fun run(args: ApplicationArguments?) {
        log.info("Dynamic property: ${dynamicProperties.baseCurrency.get()}")
    }
}