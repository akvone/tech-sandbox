package com.akvone.sandbox.data_jdbc

import com.akvone.sandbox.Features
import com.akvone.sandbox.base.SpringBootBaseTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.context.TestConstructor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post


@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class ControllerWithDataJdbcAccessTest(
    private val mockMvc: MockMvc
) : SpringBootBaseTest() {

    private val uri = "/${Features.DATA_JDBC}/colors"

    @Test
    fun `create two colors and check their creation`() {
        // Having
        getColors("""[]""")
        // When
        addColor("""{"color":"red"}""")
        addColor("""{"color":"pink"}""")
        // Then
        getColors("""[{"id":1,"color":"red"},{"id":2,"color":"pink"}]""")
    }

    private fun getColors(expectedBody: String) {
        mockMvc.get(uri).andExpect {
            status { isOk() }
            content { json(expectedBody) }
        }
    }

    private fun addColor(body: String) {
        mockMvc.post(uri) {
            contentType = MediaType.APPLICATION_JSON
            content = body
        }.andExpect {
            status { isOk() }
        }
    }

}