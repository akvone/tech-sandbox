package com.akvone.data_jdbc

import com.akvone.Features.DATA_JDBC
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(DATA_JDBC)
class ControllerWithJdbcAccess(
    private val colorRepository: ColorRepository
) {

    @PostMapping("/colors")
    fun saveColor(@RequestBody colorEntity: ColorEntity) {
        colorRepository.save(colorEntity)
    }

    @GetMapping("/colors")
    fun getColors(): MutableIterable<ColorEntity> {
        return colorRepository.findAll()
    }
}