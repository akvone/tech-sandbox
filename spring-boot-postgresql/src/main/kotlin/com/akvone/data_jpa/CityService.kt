package com.akvone.data_jpa

import com.akvone.buildLogger
import com.akvone.data_jpa.dto.City
import com.akvone.data_jpa.dto.Street
import com.akvone.data_jpa.entities.CityEntity
import com.akvone.data_jpa.repositories.CityRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CityService(
    private val cityRepository: CityRepository
) {

    val log = buildLogger()

    fun getCities(): List<City> {
        return cityRepository.findAll().map(this::buildCity)
    }

    fun getCity(id: Long): City {
        val cityEntity = cityRepository.getOne(id)
        for (street in cityEntity.streets) {
            log.info("Found street [city={}, streets={}]", cityEntity.name, street.name)
        }

        return buildCity(cityEntity)
    }

    private fun buildCity(cityEntity: CityEntity): City = City(
         cityEntity.id!!,
         cityEntity.name!!,
         cityEntity.streets.map { Street(it.id!!, it.name!!) }
     )

}
