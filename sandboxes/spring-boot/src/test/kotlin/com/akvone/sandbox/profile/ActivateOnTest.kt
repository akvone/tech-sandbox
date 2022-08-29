package com.akvone.sandbox.profile

import org.junit.jupiter.api.Test
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestPropertySource
import kotlin.test.assertEquals


@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@EnableConfigurationProperties(PropertiesDefinedByProfile::class)
@TestPropertySource(properties = ["spring.main.cloud-platform=kubernetes"])
class ActivateOnKubernetesTest(
    private val propertiesDefinedByProfile: PropertiesDefinedByProfile

) {
    @Test
    fun test() {
        assertEquals("USA", propertiesDefinedByProfile.location)
    }
}

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@EnableConfigurationProperties(PropertiesDefinedByProfile::class)
@ActiveProfiles("dev")
class ActivateOnDevProfileTest(
    private val propertiesDefinedByProfile: PropertiesDefinedByProfile

) {
    @Test
    fun test() {
        assertEquals("Russia", propertiesDefinedByProfile.location)
    }
}

@ConstructorBinding
@ConfigurationProperties(prefix = "feature.profile")
data class PropertiesDefinedByProfile(
    val location: String
)