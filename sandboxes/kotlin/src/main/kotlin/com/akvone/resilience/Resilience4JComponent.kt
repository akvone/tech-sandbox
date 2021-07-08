package com.akvone.resilience

import io.github.resilience4j.bulkhead.ThreadPoolBulkhead
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig
import io.github.resilience4j.decorators.Decorators
import io.github.resilience4j.timelimiter.TimeLimiter
import io.github.resilience4j.timelimiter.TimeLimiterConfig
import java.io.Closeable
import java.time.Duration
import java.util.concurrent.Executors

abstract class Resilience4JComponent(timeoutDuration: Duration) : Closeable {

    val threadPoolBulkhead = ThreadPoolBulkhead.of(
        "UNDEFINED", ThreadPoolBulkheadConfig.custom()
            .coreThreadPoolSize(1)
            .maxThreadPoolSize(1)
            .queueCapacity(10)
            .build()
    )

    val timeLimiter = TimeLimiter.of(
        TimeLimiterConfig.custom()
            .timeoutDuration(timeoutDuration)
            .build()
    )
    val timeLimiterScheduledThreadPool = Executors.newScheduledThreadPool(3)

    override fun close() {
        threadPoolBulkhead.close()
        timeLimiterScheduledThreadPool.shutdownNow()
    }

    fun withDecorator() {
        Decorators
            .ofCompletionStage(threadPoolBulkhead.decorateSupplier(this::withoutDecorator))
            .withTimeLimiter(timeLimiter, timeLimiterScheduledThreadPool)
            .get()
            .toCompletableFuture()
            .get()
    }

    abstract fun withoutDecorator(): String
}