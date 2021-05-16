package com.akvone.metrics.event_generator

import com.akvone.metrics.event_generator.BackgroundEventGenerator
import com.akvone.metrics.event_generator.EventGeneratorParameters
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class EventGenerationStarter(
    val backgroundEventGenerator: BackgroundEventGenerator
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val defaultParameters = EventGeneratorParameters()
        backgroundEventGenerator.changeParametersAndReschedule(defaultParameters)
    }

}