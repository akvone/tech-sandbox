package com.akvone.sandbox

import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration
@EnableAdminServer
class SpringBootAdminApp

fun main(args: Array<String>) {
	runApplication<SpringBootAdminApp>(*args)
}
