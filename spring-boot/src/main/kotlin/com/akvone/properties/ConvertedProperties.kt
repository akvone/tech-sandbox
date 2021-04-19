package com.akvone.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.util.unit.DataSize
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
    val duration: Duration,

    /**
     * Period kDoc
     */
    val period: Period,

    /**
     * DatSize kDoc
     */
    val dataSize: DataSize
)