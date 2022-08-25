package com.akvone.sandbox.metrics

import com.akvone.sandbox.Features
import com.akvone.sandbox.metrics.resilience4j.FaultTolerantService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(Features.METRICS)
class MetricsController(
    val faultTolerantService: FaultTolerantService,
) {

    @GetMapping("state")
    fun makeSuccessfulCall(
        @RequestParam("successful") successful: Boolean,
        @RequestParam("delay") delay: Long
    ) {
        faultTolerantService.execute(successful, delay)
    }

}

