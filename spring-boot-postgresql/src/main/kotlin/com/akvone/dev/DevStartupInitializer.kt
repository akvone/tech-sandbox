package com.akvone.dev

import com.akvone.data_jpa.entities.CityEntity
import com.akvone.data_jpa.entities.StreetEntity
import com.akvone.data_jpa.repositories.CityRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

@Configuration
class DevStartupInitializer(
    private val cityRepository: CityRepository
) {

    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReadyEvent() {
        val cityEntity = CityEntity()
        cityEntity.name = "Moscow"
        val streetEntity = StreetEntity()
        streetEntity.name = "Lenina"
        cityEntity.streets = listOf(streetEntity)
        cityRepository.save(cityEntity)
    }
}