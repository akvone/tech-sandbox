package com.akvone.sandbox.properties_conversion

import com.akvone.sandbox.lifecycle.MainEventListener
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.util.unit.DataSize
import java.time.Duration
import java.time.Period

@EnableConfigurationProperties(ConvertedProperties::class)
@Component
class PropertiesConversionRunner(
    val convertedProperties: ConvertedProperties,
    @Value("\${feature.properties-conversion.defaultTimeout}") val defaultTimeout: Long,
    @Value("\${feature.properties-conversion.duration}") val duration: Duration,
    @Value("\${feature.properties-conversion.period}") val period: Period,
    @Value("\${feature.properties-conversion.dataSize}") val dataSize: DataSize,
) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(MainEventListener::class.java)

    override fun run(args: ApplicationArguments?) {
        log.info(
            """From @ConfigurationProperties:
             |${convertedProperties.defaultTimeout}
             |${convertedProperties.duration}
             |${convertedProperties.period}
             |${convertedProperties.dataSize}
             """.trimMargin()
        )
        log.info(
            """From @Value:
                |$defaultTimeout
                |$duration
                |$period
                |$dataSize
            """.trimMargin()
        )
    }
}