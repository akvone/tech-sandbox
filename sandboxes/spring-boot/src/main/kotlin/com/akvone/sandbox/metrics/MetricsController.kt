package com.akvone.sandbox.metrics

import com.akvone.sandbox.Features
import com.akvone.sandbox.metrics.event_generator.BackgroundEventGenerator
import com.akvone.sandbox.metrics.event_generator.EventGeneratorParameters
import com.akvone.sandbox.metrics.resilience4j.FaultTolerantService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Features.METRICS)
class MetricsController(
    val faultTolerantService: FaultTolerantService,
    val backgroundEventGenerator: BackgroundEventGenerator
) {

    @GetMapping("state")
    fun makeSuccessfulCall(
        @RequestParam("successful") successful: Boolean,
        @RequestParam("delay") delay: Long
    ) {
        faultTolerantService.execute(successful, delay)
    }

    @PutMapping("event-generator/state")
    fun changeEventGeneratorParameters(
        @RequestBody parameters: EventGeneratorParameters
    ) {
        backgroundEventGenerator.changeParametersAndReschedule(parameters)
    }

}

