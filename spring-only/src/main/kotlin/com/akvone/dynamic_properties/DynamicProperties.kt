package com.akvone.dynamic_properties

import com.akvone.dynamic_properties.internal.Dynamic
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding


@ConstructorBinding
@ConfigurationProperties(prefix = "modules.dynamic-properties")
data class DynamicProperties(

    val baseCurrency: Dynamic<String>
)