package com.akvone.bootstrap

import org.springframework.boot.SpringApplication
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.get

@Configuration
class KubernetesEnvironmentProfileAdderPostProcessor : EnvironmentPostProcessor {

    val postProcessorConditionalProperty = "application.runtime-profile-activation.enabled"

    val envVariableToSearchForRuntimeValue: String = "KUBERNETES_NAMESPACE"
    val bootstrapPropertyMappingKey = "application.runtime-profile-activation.kubernetes"

    override fun postProcessEnvironment(environment: ConfigurableEnvironment, application: SpringApplication) {
        if (environment.getProperty(postProcessorConditionalProperty, Boolean::class.java, false)) {
            val binder = Binder.get(environment)
            val runtimePropertyValue = environment[envVariableToSearchForRuntimeValue]
                ?: throw IllegalStateException("Could not find property: $envVariableToSearchForRuntimeValue")
            val propertyMappings = binder.bind(bootstrapPropertyMappingKey, Map::class.java)
                .get() as Map<String, String>
            val additionalProfile = propertyMappings[runtimePropertyValue]
                ?: throw IllegalStateException("Could not find property: $runtimePropertyValue")

            application.setAdditionalProfiles(additionalProfile)
        }
    }
}