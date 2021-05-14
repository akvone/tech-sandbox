package com.akvone.metrics

import com.akvone.Features
import com.akvone.buildLogger
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Duration

@Component
class Resilience4jMetricsExampleService(
    circuitBreakerRegistry: CircuitBreakerRegistry
) {

    val log = buildLogger()

    val circuitBreaker: CircuitBreaker = circuitBreakerRegistry.circuitBreaker(
        "metrics-circuit-breaker", CircuitBreakerConfig.custom()
            .waitDurationInOpenState(Duration.ofSeconds(20))
            .minimumNumberOfCalls(10)
            .build()
    )

    fun execute(successful: Boolean, delay: Long) {
        circuitBreaker.executeRunnable {
            if (delay > 0) Thread.sleep(delay)

            if (successful) {
                log.info("Executed successfully")
            } else {
                throw IllegalStateException()
            }
        }
    }
}

@RestController
@RequestMapping(Features.METRICS)
@Component
class MetricsRunner(
    val resilience4JMetricsExampleService: Resilience4jMetricsExampleService
) {

    @GetMapping("state")
    fun makeSuccessfulCall(
        @RequestParam("successful") successful: Boolean,
        @RequestParam("delay", required = false) delay: Long = 0
    ) {
        resilience4JMetricsExampleService.execute(successful, delay)
    }

}