package com.akvone.data_jdbc

import org.springframework.data.repository.CrudRepository

interface ColorRepository : CrudRepository<ColorEntity, Int>

