package com.akvone.validation

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(JavaMainController::class)
class MainControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `test success`() {
        sendEndExpect("""{"names":[{"name":"hi"}]}""", MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `test failure`() {
        sendEndExpect("""{"names":[name=""]]}""", MockMvcResultMatchers.status().is4xxClientError)
        sendEndExpect("""{"names":[]}""", MockMvcResultMatchers.status().is4xxClientError)
        sendEndExpect("""{"names":null}""", MockMvcResultMatchers.status().is4xxClientError)
        sendEndExpect("", MockMvcResultMatchers.status().is4xxClientError)
    }

    private fun sendEndExpect(payload: String, resultMatcher: ResultMatcher) {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/java-main/simple")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(resultMatcher)
    }
}