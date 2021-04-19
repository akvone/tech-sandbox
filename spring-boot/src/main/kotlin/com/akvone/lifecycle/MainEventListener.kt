package com.akvone.lifecycle

import org.slf4j.LoggerFactory
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MainEventListener {
    private val log = LoggerFactory.getLogger(MainEventListener::class.java)

    @EventListener(ContextClosedEvent::class)
    fun onContextClosedEvent(event: ContextClosedEvent?) {
        log.info(event.toString())
    }
}