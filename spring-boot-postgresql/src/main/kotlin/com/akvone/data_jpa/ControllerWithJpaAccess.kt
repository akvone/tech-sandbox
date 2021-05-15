package com.akvone.data_jpa

import com.akvone.Features
import com.akvone.buildLogger
import com.akvone.data_jpa.entities.CityEntity
import com.akvone.data_jpa.repositories.CityRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(Features.DATA_JPA)
class ControllerWithJpaAccess(
    private val cityRepository: CityRepository
) {

    val log = buildLogger()

    @GetMapping("/cities/{id}")
    fun getCity(@PathVariable id: Long): CityEntity {
        val city = cityRepository.getOne(id)
        for (street in city.streets) {
            log.info("Found street [city={}, streets={}]", city.name, street.name)
        }

        return city
    }
}