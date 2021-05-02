package com.akvone

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ApplicationWithPostgresql

fun main() {
    SpringApplication.run(ApplicationWithPostgresql::class.java)
}