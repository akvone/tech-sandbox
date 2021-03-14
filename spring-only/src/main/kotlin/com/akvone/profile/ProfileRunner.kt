package com.akvone.profile

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@Component
@EnableConfigurationProperties(DefinedByProfileProperties::class)
class ProfileRunner(
    private val properties: DefinedByProfileProperties
) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(ApplicationRunner::class.java)

    override fun run(args: ApplicationArguments?) {
        log.info("Detected location (by profile) ${properties.location}")
    }
}