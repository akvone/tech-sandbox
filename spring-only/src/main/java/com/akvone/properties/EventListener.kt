package com.akvone.properties

import com.akvone.lifecycle.MainEventListener
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@EnableConfigurationProperties(MainServicesProperties::class)
@Component
class EventListener(
    private val mainServicesProperties: MainServicesProperties,
) {

    private val log = LoggerFactory.getLogger(MainEventListener::class.java)

    @EventListener(ContextRefreshedEvent::class)
    fun onContextRefresh() {
        log.info("EventListener from Long: ${mainServicesProperties.defaultTimeout}")
        log.info("EventListener from Duration: ${mainServicesProperties.newDefaultTimeout.toMillis()}")
    }
}