package com.akvone.sandbox.security

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(controllers = [SecurityController::class])
internal class SecurityControllerWebMvcTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    @WithMockUser(authorities = ["SSO"])
    fun `test secured endpoint with authorities`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/secured")
        ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `test secured endpoint without authorities`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/secured")
        ).andExpect(MockMvcResultMatchers.status().isForbidden)
    }
}