package com.akvone.properties_state

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Configuration
@EnableConfigurationProperties(
    value = [
        NonRefreshedProperties::class,
        RefreshedProperties::class
    ]
)
class PropertiesConfiguration

@ConfigurationProperties("properties-state")
class RefreshedProperties {
    var forMutable = 0
}

/**
 * You can find
 * a [discussion](https://github.com/spring-cloud/spring-cloud-config/issues/1547)
 * why value-type properties could not be refreshed
 */
@ConfigurationProperties("properties-state")
@ConstructorBinding
class NonRefreshedProperties(val forImmutable: Int)

@RestController
@RequestMapping("properties-state")
class RefreshedPropertiesController(
    val refreshedProperties: RefreshedProperties,
    val nonRefreshedProperties: NonRefreshedProperties
) {

    @GetMapping()
    fun getProperty(): Map<String, Int> {
        return mapOf(
            "forMutable" to refreshedProperties.forMutable,
            "forImmutable" to nonRefreshedProperties.forImmutable
        )
    }

}