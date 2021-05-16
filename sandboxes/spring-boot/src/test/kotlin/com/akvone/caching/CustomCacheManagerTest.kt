package com.akvone.caching

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor
import org.springframework.test.context.TestConstructor.AutowireMode.ALL

@SpringBootTest
@TestConstructor(autowireMode = ALL)
@ActiveProfiles("prod", "feature-custom-cache")
internal class CustomCacheManagerTest(
    val serviceCachedByCustomCacheManager: ServiceCachedByCustomCacheManager
) {

    @Test
    fun test() {
        assertDoesNotThrow {
            serviceCachedByCustomCacheManager.putObjectIntoCacheWhichThrowsExceptionOnPut()
        }
        assertThrows<IllegalStateException> {
            serviceCachedByCustomCacheManager.tryToPutObjectButThrowExceptionOnItsCalculation()
        }
    }
}