package com.akvone.sandbox.data_jpa

import com.akvone.sandbox.Features
import com.akvone.sandbox.data_jpa.dto.City
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(Features.DATA_JPA)
class ControllerWithJpaAccess(
    private val cityService: CityService
) {

    @GetMapping("/cities")
    fun getCities(): List<City> {
        return cityService.getCities()
    }

    @GetMapping("/cities/{id}")
    fun getCity(@PathVariable id: Long): City {
        return cityService.getCity(id)
    }
}