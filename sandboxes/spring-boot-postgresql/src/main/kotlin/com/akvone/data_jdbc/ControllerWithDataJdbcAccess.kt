package com.akvone.data_jdbc

import com.akvone.Features
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Features.DATA_JDBC)
class ControllerWithDataJdbcAccess(
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