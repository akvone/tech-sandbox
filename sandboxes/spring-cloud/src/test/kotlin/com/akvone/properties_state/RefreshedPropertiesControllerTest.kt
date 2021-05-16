package com.akvone.properties_state

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
internal class RefreshedPropertiesControllerTest {

    val objectMapper = jacksonObjectMapper()

    @Autowired
    lateinit var mockMvc: MockMvc


    @Test
    fun getProperty() {
        getAndExpect("""{"forMutable"=1,"forImmutable"=1}""")
        updateProperty("properties-state.forMutable", 2)
        updateProperty("properties-state.forImmutable", 3)
        getAndExpect("""{"forMutable"=2,"forImmutable"=1}""")
    }

    private fun updateProperty(propertyName: String, newPropertyValue: Int) {
        mockMvc.perform(
            post("/actuator/env")
                .contentType(MediaType.APPLICATION_JSON)
                .content(constructStringPayload(propertyName, newPropertyValue))
        ).andExpect(status().isOk)
    }

    private fun getAndExpect(jsonAsString: String) {
        mockMvc.get("/properties-state")
            .andExpect {
                content { json(jsonAsString) }
            }
    }

    private fun constructStringPayload(propertyName: String, newPropertyValue: Int) =
        objectMapper.writeValueAsString(constructPayload(propertyName, newPropertyValue))

    private fun constructPayload(propertyName: String, newPropertyValue: Int) = mapOf<String, Any>(
        "name" to propertyName, "value" to newPropertyValue
    )
}