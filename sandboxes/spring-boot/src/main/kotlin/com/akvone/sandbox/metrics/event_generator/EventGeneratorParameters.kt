package com.akvone.sandbox.metrics.event_generator

import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class EventGeneratorParameters(

    val taskDelay: Long = 0,

    @Min(1)
    @Max(500)
    val eventRate: Int = 1,

    @Min(0)
    @Max(1)
    val failureChance: Double = 0.0
) {
    fun millisPeriodBetweenEvents(): Long = (1000 / eventRate).toLong()
}