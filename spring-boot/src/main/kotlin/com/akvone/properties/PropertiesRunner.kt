package com.akvone.properties

import com.akvone.lifecycle.MainEventListener
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
class PropertiesRunner(
    val convertedProperties: ConvertedProperties,
    @Value("\${modules.properties.defaultTimeout}") val defaultTimeout: Long,
    @Value("\${modules.properties.duration}") val duration: Duration,
    @Value("\${modules.properties.period}") val period: Period,
    @Value("\${modules.properties.dataSize}") val dataSize: DataSize,
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