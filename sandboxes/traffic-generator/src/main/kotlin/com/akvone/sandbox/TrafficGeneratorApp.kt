package com.akvone.sandbox

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan
class TrafficGeneratorApp // TODO: Replace with some existing solution

fun main() {
    runApplication<TrafficGeneratorApp>()
}