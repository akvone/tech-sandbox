package com.akvone.dynamic_properties.internal

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
class DynamicPropertiesBeanPostProcessor : BeanPostProcessor {
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        val clazz = bean::class.java
        if (clazz.isAnnotationPresent(ConfigurationProperties::class.java)) {
            val configurationPropertiesAnnotation = clazz.getAnnotation(ConfigurationProperties::class.java)

            clazz.declaredFields.forEach { field ->
                if (field.type == Dynamic::class.java) {
                    val declaredMethod = clazz.getDeclaredMethod("get${field.name.capitalize()}")
                    val dynamicValue = declaredMethod.invoke(bean) as Dynamic<*>
                    dynamicValue.fullPropertyName = "${configurationPropertiesAnnotation.prefix}.${field.name}"
                }
            }
        }

        return super.postProcessBeforeInitialization(bean, beanName)
    }

}