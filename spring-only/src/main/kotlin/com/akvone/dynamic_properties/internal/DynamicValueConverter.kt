package com.akvone.dynamic_properties.internal

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.core.convert.TypeDescriptor
import org.springframework.core.convert.converter.GenericConverter
import org.springframework.stereotype.Component

@Component
@ConfigurationPropertiesBinding
class DynamicValueConverter : GenericConverter {
    override fun getConvertibleTypes(): Set<GenericConverter.ConvertiblePair> {
        return setOf(GenericConverter.ConvertiblePair(String::class.java, DynamicValue::class.java))
    }

    override fun convert(source: Any?, sourceType: TypeDescriptor, targetType: TypeDescriptor): DynamicValue<*> {
        return DynamicValue(source)
    }
}