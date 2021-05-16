package com.akvone.metrics

import com.akvone.Features
import com.akvone.metrics.event_generator.BackgroundEventGenerator
import com.akvone.metrics.event_generator.EventGeneratorParameters
import com.akvone.metrics.resilience4j.FaultTolerantService
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

