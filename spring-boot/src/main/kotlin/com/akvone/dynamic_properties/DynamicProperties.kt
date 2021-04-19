package com.akvone.dynamic_properties

import com.akvone.dynamic_properties.internal.DynamicProperty
import com.akvone.dynamic_properties.internal.DynamicPropertyKey
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "modules.dynamic-properties")
data class DynamicProperties(

    @DynamicPropertyKey("modules.dynamic-properties.baseCurrency")
    val baseCurrency: DynamicProperty<String>
)