package com.akvone

import com.akvone.caching.CachedService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class MainRunner(
    private val cachedService: CachedService
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        cachedService.constructLargeObject("200")
        cachedService.constructLargeObject("200")
    }
}