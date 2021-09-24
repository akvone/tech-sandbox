package com.akvone.sandbox.encrypted_properties

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix="encrypted")
@ConstructorBinding
data class EncryptedProperties(
    val value: String
)

@Component
@EnableConfigurationProperties(EncryptedProperties::class)
class EncryptedPropertiesRunner(
    val encryptedProperties: EncryptedProperties
) : ApplicationRunner {

    private val log = LoggerFactory.getLogger(EncryptedPropertiesRunner::class.java)

    override fun run(args: ApplicationArguments?) {
        log.info("Encrypted: $encryptedProperties")
    }
}