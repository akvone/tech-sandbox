package com.akvone.dynamic_properties

import com.akvone.dynamic_properties.internal.DynamicValue
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConstructorBinding
@ConfigurationProperties(prefix = "modules.dynamic-properties")
data class DynamicProperties(

    val baseCurrency: DynamicValue<String>
)