package com.akvone.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConfigurationProperties("main.services")
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