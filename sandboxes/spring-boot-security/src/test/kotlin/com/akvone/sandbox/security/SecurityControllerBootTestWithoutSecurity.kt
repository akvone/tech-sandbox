package com.akvone.sandbox.security

import com.akvone.sandbox.base.DisableSecurityInTest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import kotlin.test.assertEquals

@DisableSecurityInTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class SecurityControllerBootTestWithoutSecurity {
    @LocalServerPort
    var port: Int = 0

    val restTemplate: TestRestTemplate = TestRestTemplate()

    @Test
    fun `test secured endpoint without authorities`() {
        assertEquals(
            200,
            restTemplate.getForEntity("http://localhost:$port/api/secured", Object::class.java).statusCodeValue
        )
    }

}