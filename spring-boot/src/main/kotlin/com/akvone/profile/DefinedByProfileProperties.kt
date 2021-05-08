package com.akvone.profile

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "feature.profile")
data class DefinedByProfileProperties(

    val location: String
)