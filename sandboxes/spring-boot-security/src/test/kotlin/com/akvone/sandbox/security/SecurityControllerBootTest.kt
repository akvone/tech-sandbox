package com.akvone.sandbox.security

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import kotlin.test.assertEquals

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class SecurityControllerBootTest {

    @LocalServerPort
    var port: Int = 0

    val restTemplate: TestRestTemplate = TestRestTemplate()

    @Test
    fun `test secured endpoint without authorities`() {
        assertEquals(
            403,
            restTemplate.getForEntity("http://localhost:$port/api/secured", Object::class.java).statusCodeValue
        )
    }
}
