package com.akvone.sandbox.data_jpa.dto

data class City(
    val id: Long,
    var name: String,

    val streets: List<Street>
)

data class Street(
    val id: Long,
    val name: String
)