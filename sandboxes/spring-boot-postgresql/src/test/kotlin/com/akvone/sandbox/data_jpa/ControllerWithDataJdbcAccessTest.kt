package com.akvone.sandbox.data_jpa

import com.akvone.sandbox.Features
import com.akvone.sandbox.base.SpringBootBaseTest
import com.akvone.sandbox.data_jpa.dto.City
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get


@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class ControllerWithDataJdbcAccessTest(
    private val mockMvc: MockMvc
) : SpringBootBaseTest() {

    private val rootUri = "/${Features.DATA_JPA}/cities"

    @Test
    fun `check that the predefined cities will be stored in DB`() {
        // When
        val cities = getCities()
        // Then
        Assertions.assertThat(cities).hasSize(2)
        Assertions.assertThat(cities.map { it.name }.toSet()).isEqualTo(setOf("Moscow", "Saint Petersburg"))
    }

    val objectMapper = jacksonObjectMapper()

    private fun getCities(): List<City> {
        return mockMvc.get(rootUri).andExpect {
            status { isOk() }
        }.andReturn().response.contentAsString.let {
            objectMapper.readValue(it, object : TypeReference<List<City>>() {})
        }
    }

}