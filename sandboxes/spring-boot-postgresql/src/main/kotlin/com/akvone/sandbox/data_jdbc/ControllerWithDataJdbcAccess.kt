package com.akvone.sandbox.data_jdbc

import com.akvone.sandbox.Features
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