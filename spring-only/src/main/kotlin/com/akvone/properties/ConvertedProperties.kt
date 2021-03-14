package com.akvone.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration
import java.time.Period

@ConfigurationProperties("modules.properties")
@ConstructorBinding
data class ConvertedProperties(

    /**
     * Old timeout description
     */
    val defaultTimeout: Long,

    /**
     * New timeout description
     */
    val newDefaultTimeout: Duration,

    /**
     * Period kdoc
     */
    val period: Period
)