package com.akvone.sandbox.resilience

import io.github.resilience4j.bulkhead.ThreadPoolBulkhead
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig
import io.github.resilience4j.decorators.Decorators
import io.github.resilience4j.timelimiter.TimeLimiter
import io.github.resilience4j.timelimiter.TimeLimiterConfig
import org.junit.jupiter.api.Test
import java.io.Closeable
import java.time.Duration
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger


internal class Resilience4JTest {

    @Test
    fun `when TimeLimiter is combined with ThreadPoolBulkhead then waiting tasks will throw timeout exception even before execution start`() {
        val resilience4JComponent = AbstractResilience4JComponent(
            sleepDuration = Duration.ofMillis(400),
            timeLimiterDuration = Duration.ofMillis(500)
        )

        resilience4JComponent.use { r4jc ->
            (1..5)
                .map { Thread { r4jc.runWithResilience4J() } }
                .onEach { it.start() }
                .forEach { it.join() }

            assert(r4jc.atomicInteger.get() == 1)
        }
    }

    class AbstractResilience4JComponent(
        private val sleepDuration: Duration,
        timeLimiterDuration: Duration,
    ) : Closeable {

        private val threadPoolBulkhead = ThreadPoolBulkhead.of(
            "UNDEFINED", ThreadPoolBulkheadConfig.custom()
                .coreThreadPoolSize(1)
                .maxThreadPoolSize(1)
                .queueCapacity(10)
                .build()
        )
        private val timeLimiter = TimeLimiter.of(
            TimeLimiterConfig.custom()
                .timeoutDuration(timeLimiterDuration)
                .build()
        )
        private val timeLimiterScheduledThreadPool = Executors.newScheduledThreadPool(3)

        val atomicInteger = AtomicInteger(0)

        override fun close() {
            threadPoolBulkhead.close()
            timeLimiterScheduledThreadPool.shutdownNow()
        }

        fun runWithResilience4J() {
            Decorators.ofCompletionStage(threadPoolBulkhead.decorateSupplier(this::run))
                .withTimeLimiter(timeLimiter, timeLimiterScheduledThreadPool)
                .get()
                .toCompletableFuture()
                .get()
        }

        fun run(): String {
            Thread.sleep(sleepDuration.toMillis())
            atomicInteger.incrementAndGet()
            return "done"
        }
    }
}