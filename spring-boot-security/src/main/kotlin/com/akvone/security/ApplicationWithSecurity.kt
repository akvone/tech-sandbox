package com.akvone.security

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ApplicationWithSecurity

fun main() {
    SpringApplication.run(ApplicationWithSecurity::class.java)
}