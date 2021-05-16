package com.akvone.data_jpa.initialization

import com.akvone.data_jpa.entities.CityEntity
import com.akvone.data_jpa.entities.StreetEntity
import com.akvone.data_jpa.repositories.CityRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

@Configuration
@EnableConfigurationProperties(DataJpaProperties::class)
class PredefinedCitiesInitializer(
    private val cityRepository: CityRepository,
    private val dataJpaProperties: DataJpaProperties
) {

    @EventListener(ApplicationReadyEvent::class)
    fun onApplicationReadyEvent() {
        val existingCities = cityRepository.findAll().map { it.name }.toSet()

        dataJpaProperties.cities
            .filter { !existingCities.contains(it.name) }
            .forEach { city ->
                val cityEntity = CityEntity().apply {
                    name = city.name
                    streets = city.streets.map { streetName ->
                        StreetEntity().also { street -> street.name = streetName }
                    }
                }
                cityRepository.save(cityEntity)
            }
    }
}

@ConfigurationProperties(prefix = "data-jpa")
@ConstructorBinding
data class DataJpaProperties(
    val cities: List<City>
)

@ConstructorBinding
data class City(
    val name: String,
    val streets: List<String>
)
