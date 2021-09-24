package com.akvone.sandbox.dynamic_properties.internal

import org.slf4j.LoggerFactory

data class DynamicProperty<T>(
    private val initialValue: T,
    private val fullPropertyName: String
) {
    private val log = LoggerFactory.getLogger(DynamicProperty::class.java)

    fun get(): T {
        return getPropertyInExternalSystem() ?: initialValue
    }

    private fun getPropertyInExternalSystem(): T? {
        // Do http call or look in cache
        log.info("No dynamic value for `$fullPropertyName` is present")
        return null
    }
}