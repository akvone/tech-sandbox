package com.akvone

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class ApplicationSpringOnly

fun main() {
    SpringApplication.run(ApplicationSpringOnly::class.java)
}
