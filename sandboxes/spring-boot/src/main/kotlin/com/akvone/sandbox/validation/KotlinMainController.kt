package com.akvone.sandbox.validation

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("kotlin-main")
class KotlinMainController {

    private val log = LoggerFactory.getLogger(KotlinMainController::class.java)

    @GetMapping("simple")
    fun getSimpleResult(@RequestParam(required = false) flag: Boolean): Boolean {
        return flag
    }

    @PostMapping("simple")
    fun getSimpleResult(@RequestBody @Valid request: SimpleRequest) {
        log.info(request.toString())
    }
}