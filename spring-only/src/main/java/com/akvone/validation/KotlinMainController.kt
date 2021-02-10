package com.akvone.validation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("kotlin-main")
class KotlinMainController {

    @GetMapping("simple")
    fun getSimpleResult(@RequestParam(required = false) flag: Boolean): Boolean {
        return flag
    }
}