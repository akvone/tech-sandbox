package com.akvone.data_jdbc

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


@Table("color")
data class ColorEntity(
    @Id
    val id: Int?,
    val color: String
)