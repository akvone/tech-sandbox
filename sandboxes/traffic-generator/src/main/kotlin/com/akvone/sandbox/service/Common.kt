package com.akvone.sandbox.service

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties
@ConstructorBinding
data class ServicesProperties(
    val services: Map<Service, ServiceProperties>
) {

    fun get(service: Service): ServiceProperties = services[service]!!
}

enum class Service {
    SPRING_BOOT_ADMIN,
    SPRING_CLOUD,
    SPRING_BOOT,
    SPRING_BOOT_SECURITY,
    SPRING_CLOUD_GATEWAY,
    SPRING_BOOT_POSTGRESQL,
    TRAFFIC_GENERATOR
}

data class ServiceProperties(
    val url: String
)