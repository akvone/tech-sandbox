package com.akvone.sandbox.dynamic_properties.internal

import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport.*
import org.springframework.core.convert.TypeDescriptor
import org.springframework.core.convert.converter.GenericConverter
import org.springframework.util.PropertyPlaceholderHelper

class DynamicValueConverter : GenericConverter {
    private val placeholderHelper = PropertyPlaceholderHelper(
        DEFAULT_PLACEHOLDER_PREFIX,
        DEFAULT_PLACEHOLDER_SUFFIX,
        DEFAULT_VALUE_SEPARATOR,
        false
    )

    override fun getConvertibleTypes(): Set<GenericConverter.ConvertiblePair> {
        return setOf(GenericConverter.ConvertiblePair(String::class.java, DynamicProperty::class.java))
    }

    override fun convert(source: Any?, sourceType: TypeDescriptor, targetType: TypeDescriptor): DynamicProperty<*> {
        if (targetType.hasAnnotation(DynamicPropertyKey::class.java)) {
            return DynamicProperty(source, targetType.getAnnotation(DynamicPropertyKey::class.java)!!.value)
        } else if (targetType.hasAnnotation(Value::class.java)) {
            val value = targetType.getAnnotation(Value::class.java)!!.value
            val valueWithoutMetaSymbols = placeholderHelper.replacePlaceholders(value) { it }
            return DynamicProperty(source, valueWithoutMetaSymbols)
        }
        throw IllegalArgumentException()
    }
}