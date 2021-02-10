package com.akvone

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.SpringApplication

@SpringBootApplication
class ApplicationSpringOnly

fun main() {
    SpringApplication.run(ApplicationSpringOnly::class.java)
}
