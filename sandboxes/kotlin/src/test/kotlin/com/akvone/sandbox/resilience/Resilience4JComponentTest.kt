package com.akvone.sandbox.resilience

import org.junit.jupiter.api.Test
import java.time.Duration
import java.util.concurrent.atomic.AtomicInteger


internal class Resilience4JComponentTest {

    @Test
    fun `test, when TimeLimiter is combined with ThreadPoolBulkhead then waiting tasks will throw timeout exception even before execution start`() {
        val resilienceComponent = object : Resilience4JComponent(Duration.ofMillis(500)) {
            val atomicInteger = AtomicInteger(0)

            override fun withoutDecorator(): String {
                Thread.sleep(400)
                atomicInteger.incrementAndGet()
                return "done"
            }
        }

        resilienceComponent.use { r4jc ->
            (1..5)
                .map { Thread { r4jc.withDecorator() } }
                .onEach { it.start() }
                .forEach { it.join() }

            assert(r4jc.atomicInteger.get() == 1)
        }
    }
}