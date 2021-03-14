package com.akvone.properties

import com.akvone.lifecycle.MainEventListener
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@EnableConfigurationProperties(ConvertedProperties::class)
@Component
class PropertiesRunner(
    private val convertedProperties: ConvertedProperties,
) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(MainEventListener::class.java)

    override fun run(args: ApplicationArguments?) {
        log.info("From Long: ${convertedProperties.defaultTimeout}")
        log.info("From Duration: ${convertedProperties.newDefaultTimeout.toMillis()}")
        log.info("From Period: ${convertedProperties.period.toTotalMonths()}")
    }
}