package com.akvone

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication

@SpringBootApplication
class ApplicationWithPostgresql

fun main() {
    SpringApplication.run(ApplicationWithPostgresql::class.java)
}