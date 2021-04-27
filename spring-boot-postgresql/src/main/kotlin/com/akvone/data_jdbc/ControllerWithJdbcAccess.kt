package com.akvone.data_jdbc

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("jdbc")
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