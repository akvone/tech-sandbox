package com.akvone.sandbox.data_jdbc

import org.springframework.data.repository.CrudRepository

interface ColorRepository : CrudRepository<ColorEntity, Int>