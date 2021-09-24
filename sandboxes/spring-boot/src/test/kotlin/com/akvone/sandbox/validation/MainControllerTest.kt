package com.akvone.sandbox.validation

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(controllers = [JavaMainController::class, KotlinMainController::class])
class MainControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `test success`() {
        sendEndExpect("""{"names": [ {"name":"hi"} ] }""", MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `test failure`() {
        sendEndExpect("""{"names":[name=""]]}""", MockMvcResultMatchers.status().isBadRequest)
        sendEndExpect("""{"names":[]}""", MockMvcResultMatchers.status().isBadRequest)
        sendEndExpect("""{"names":null}""", MockMvcResultMatchers.status().isBadRequest)
        sendEndExpect("", MockMvcResultMatchers.status().isBadRequest)
    }

    private fun sendEndExpect(payload: String, resultMatcher: ResultMatcher) {
        sendAndExpect(payload, resultMatcher, "/java-main/simple")
        sendAndExpect(payload, resultMatcher, "/kotlin-main/simple")
    }

    private fun sendAndExpect(payload: String, resultMatcher: ResultMatcher, rootUrl: String) {
        mockMvc.perform(
            MockMvcRequestBuilders.post(rootUrl)
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(resultMatcher)
    }
}