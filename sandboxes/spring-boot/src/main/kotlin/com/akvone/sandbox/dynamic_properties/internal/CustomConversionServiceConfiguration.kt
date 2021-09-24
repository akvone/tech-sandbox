package com.akvone.sandbox.dynamic_properties.internal

import org.springframework.boot.convert.ApplicationConversionService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomConversionServiceConfiguration {

    @Bean
    fun conversionService() = ApplicationConversionService().also { it.addConverter(DynamicValueConverter()) }
}