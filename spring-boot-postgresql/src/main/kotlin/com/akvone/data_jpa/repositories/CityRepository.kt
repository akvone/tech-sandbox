package com.akvone.data_jpa.repositories

import com.akvone.data_jpa.entities.CityEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CityRepository : JpaRepository<CityEntity, Long>