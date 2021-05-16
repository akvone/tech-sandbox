package com.akvone.metrics.resilience4j

import com.akvone.buildLogger
import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry
import org.springframework.stereotype.Component
import java.time.Duration
import kotlin.random.Random

@Component
class FaultTolerantService(
    circuitBreakerRegistry: CircuitBreakerRegistry
) {

    val log = buildLogger()

    val circuitBreaker: CircuitBreaker = circuitBreakerRegistry.circuitBreaker(
        "metrics-resilience4j-circuit-breaker", CircuitBreakerConfig.custom()
            .failureRateThreshold(90f)
            .waitDurationInOpenState(Duration.ofSeconds(20))
            .slowCallDurationThreshold(Duration.ofMillis(100))
            .build()
    )

    fun execute(failureChance: Double, delay: Long) {
        execute(isSuccessful(failureChance), delay)
    }

    fun isSuccessful(failureChance: Double): Boolean {
        if (failureChance < 0 || failureChance > 1) {
            throw IllegalArgumentException()
        }

        return Random.nextDouble() > failureChance
    }


    fun execute(successful: Boolean, delay: Long) {
        circuitBreaker.executeRunnable {
            if (delay > 0) Thread.sleep(delay)

            if (successful) {
                log.info("Executed successfully")
            } else {
                throw IllegalStateException("Executed with exception")
            }
        }
    }
}