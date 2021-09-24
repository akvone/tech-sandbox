package com.akvone.sandbox.metrics.event_generator

import com.akvone.sandbox.buildLogger
import com.akvone.sandbox.metrics.resilience4j.FaultTolerantService
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.annotation.PreDestroy

// TODO: Reimplement or find some existing solution
@Component
class BackgroundEventGenerator(
    val faultTolerantService: FaultTolerantService
) {

    val log = buildLogger()

    private val scheduledExecutorService: ScheduledExecutorService = Executors.newScheduledThreadPool(100)
    @Volatile
    private var scheduledFuture: ScheduledFuture<*>
        = scheduledExecutorService.schedule({}, 0, TimeUnit.SECONDS) // TODO: Use some mocked ScheduledFuture instead


    @PreDestroy
    fun preDestroy(){
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
            faultTolerantService.execute(parameters.failureChance, parameters.taskDelay)
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