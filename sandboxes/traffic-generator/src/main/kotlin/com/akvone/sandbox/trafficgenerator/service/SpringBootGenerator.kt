package com.akvone.sandbox.trafficgenerator.service

import com.akvone.sandbox.buildLogger
import com.akvone.sandbox.trafficgenerator.service.Service.SPRING_BOOT
import kotlinx.coroutines.runBlocking
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitExchange
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.annotation.PreDestroy
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import kotlin.random.Random

@RestController
class SpringBootTrafficController(
    val springBootTrafficGenerator: SpringBootTrafficGenerator
) {

    @PutMapping("event-generator/state")
    fun changeEventGeneratorParameters(@RequestBody parameters: EventGeneratorParameters) {
        springBootTrafficGenerator.changeParametersAndReschedule(parameters)
    }

}

@Component
class EventGenerationStarter(
    val springBootTrafficGenerator: SpringBootTrafficGenerator
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        val defaultParameters = EventGeneratorParameters()
        springBootTrafficGenerator.changeParametersAndReschedule(defaultParameters)
    }

}

@Component
class SpringBootTrafficGenerator(
    webClientBuilder: WebClient.Builder,
    val servicesProperties: ServicesProperties
) {

    val log = buildLogger()
    private val webClient = webClientBuilder.build()

    private val scheduledExecutorService: ScheduledExecutorService = Executors.newScheduledThreadPool(100)

    @Volatile
    private var scheduledFuture: ScheduledFuture<*> =
        scheduledExecutorService.schedule({}, 0, TimeUnit.SECONDS) // TODO: Use some mocked ScheduledFuture instead

    @PreDestroy
    fun preDestroy() {
        log.info("Shutting down ScheduledExecutorService")
        scheduledExecutorService.shutdownNow()
        scheduledExecutorService.awaitTermination(1, TimeUnit.SECONDS)
        log.info("ScheduledExecutorService was terminated")
    }

    @Synchronized
    fun changeParametersAndReschedule(parameters: EventGeneratorParameters) {
        scheduledFuture.cancel(true)

        while (!scheduledFuture.isDone) {
            log.info("The previous task is not cancelled yet...")
            Thread.sleep(1000)
        }

        scheduledFuture = schedule(parameters) {
            execute(parameters.failureChance, parameters.taskDelay)
        }
    }


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
        runBlocking {
            try {
                val url = servicesProperties.get(SPRING_BOOT).url
                val status = webClient.get()
                    .uri("$url/metrics/state?successful=$successful&delay=$delay")
                    .awaitExchange { it.statusCode() }
                log.info("spring-boot metrics $status")
            } catch (e: Exception) {
                log.error("Can't execute: ${e.message}")
            }
        }
    }

    fun schedule(parameters: EventGeneratorParameters, runnable: () -> Unit): ScheduledFuture<*> {
        val swallowingExceptionRunnable = Runnable {
            try {
                runnable()
            } catch (exception: Exception) {
                log.error("Exception is swallowed: $exception")
            }
        }

        return scheduledExecutorService.scheduleAtFixedRate(
            swallowingExceptionRunnable, 0, parameters.millisPeriodBetweenEvents(), TimeUnit.MILLISECONDS
        )
    }

}

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