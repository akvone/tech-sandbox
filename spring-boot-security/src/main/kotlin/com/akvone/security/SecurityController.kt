package com.akvone.security

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/secured")
class SecurityController {

    val log: Logger = LoggerFactory.getLogger(SecurityController::class.java)

    @GetMapping
    fun secured() {
        log.info("OK")
    }
}