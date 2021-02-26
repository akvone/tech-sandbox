package com.akvone.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConfigurationProperties("modules.properties")
@ConstructorBinding
data class MainServicesProperties(

    /**
     * Old timeout description
     */
    val defaultTimeout: Long,

    /**
     * New timeout description
     */
    val newDefaultTimeout: Duration
)