package com.akvone.dynamic_properties.internal

import org.slf4j.LoggerFactory

data class Dynamic<T>(
    val initialValue: T
) {
    private val log = LoggerFactory.getLogger(Dynamic::class.java)

    internal lateinit var fullPropertyName: String

    fun get(): T {
        return getPropertyInExternalSystem() ?: initialValue
    }

    private fun getPropertyInExternalSystem(): T? {
        // Do http call or look in cache
        log.info("No dynamic value for `$fullPropertyName` is present")
        return null
    }
}