package com.akvone.sandbox.profile

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@Component
@EnableConfigurationProperties(PropertiesDefinedByProfile::class)
class ProfileRunner(
    private val propertiesDefinedByProfile: PropertiesDefinedByProfile
) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(ApplicationRunner::class.java)

    override fun run(args: ApplicationArguments?) {
        log.info("Detected location (by profile) ${propertiesDefinedByProfile.location}")
    }
}

@ConstructorBinding
@ConfigurationProperties(prefix = "feature.profile")
data class PropertiesDefinedByProfile(

    val location: String
)