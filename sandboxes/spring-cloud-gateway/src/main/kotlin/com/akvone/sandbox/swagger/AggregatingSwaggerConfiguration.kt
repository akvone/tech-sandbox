package com.akvone.sandbox.swagger

import com.akvone.sandbox.swagger.AddSwaggerUrlsBeanPostProcessor.Companion.generateSwaggerProxyRootPath
import com.akvone.sandbox.swagger.AggregatingSwaggerProperties.Companion.ROUTES_ID_PROPERTY
import com.akvone.sandbox.swagger.SwaggerMetadataServersReplacingGatewayFilterFactory.Companion.ReplaceConfig
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.AbstractSwaggerUiConfigProperties.SwaggerUrl
import org.springdoc.core.SwaggerUiConfigProperties
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.context.properties.bind.Binder
import org.springframework.cloud.gateway.config.GatewayProperties
import org.springframework.cloud.gateway.filter.FilterDefinition
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository
import org.springframework.cloud.gateway.route.RouteDefinition
import org.springframework.cloud.gateway.route.RouteDefinitionRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.*

@Configuration
@EnableConfigurationProperties(AggregatingSwaggerProperties::class)
class AggregatingSwaggerConfiguration

@ConstructorBinding
@ConfigurationProperties(ROUTES_ID_PROPERTY)
data class AggregatingSwaggerProperties(
    internal val routes: Map<String, SwaggerRouteDefinition>
) {
    companion object {
        const val ROUTES_ID_PROPERTY = "custom.swagger-ui"
    }
}

data class SwaggerRouteDefinition(
    internal val contextPath: String
)

@Configuration
class AddSwaggerUrlsBeanPostProcessor(environment: Environment) : BeanPostProcessor {

    private final val binder = Binder.get(environment)
    private final val aggregatingSwaggerProperties =
        binder.bind(ROUTES_ID_PROPERTY, AggregatingSwaggerProperties::class.java).get()

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (bean is SwaggerUiConfigProperties) {
            bean.urls = aggregatingSwaggerProperties.routes.keys.map { routeId ->
                SwaggerUrl(routeId, "${generateSwaggerProxyRootPath(routeId)}/v3/api-docs", "$routeId microservice")
            }.toSet()
        }

        return super.postProcessAfterInitialization(bean, beanName)
    }

    companion object {
        internal fun generateSwaggerProxyRootPath(routeId: String) = "/$routeId-swagger"
    }

}


@Configuration
class SwaggerRouteDefinitionLocatorConfiguration(
    private val aggregatingSwaggerProperties: AggregatingSwaggerProperties,
    private val gatewayProperties: GatewayProperties,
) {

    @Bean
    fun swaggerProxyRouteDefinitionRepository(): RouteDefinitionRepository {
        val swaggerRouteDefinitions: List<RouteDefinition> = aggregatingSwaggerProperties.routes.map { route ->
            val routeId = route.key
            val baseRouteDefinition = gatewayProperties.routes
                .find { it.id == routeId }
                ?: throw IllegalStateException("Route with $routeId does not exist")
            val swaggerProxyRouteDefinition = RouteDefinition()
            swaggerProxyRouteDefinition.apply {
                id = "$routeId-swagger-proxy"
                uri = baseRouteDefinition.uri
                predicates = listOf(PredicateDefinition("Path=${generateSwaggerProxyRootPath(routeId)}/v3/api-docs"))
                filters = listOf(
                    FilterDefinition("RewritePath=${generateSwaggerProxyRootPath(routeId)}/?(?<segment>.*), /\$\\{segment}"), // TODO: Rewrite path without regexps
                    FilterDefinition("SwaggerMetadataServersReplacing=${route.value.contextPath}") // TODO: Add param name to make it simpler to understand
                )
            }
        }

        return InMemoryRouteDefinitionRepository().also { repository ->
            swaggerRouteDefinitions.forEach {
                repository.save(Mono.just(it)).block()
            }
        }
    }

}

@Component
class SwaggerMetadataServersReplacingGatewayFilterFactory(
    private val modifyResponseBodyFilterFactory: ModifyResponseBodyGatewayFilterFactory
) : AbstractGatewayFilterFactory<ReplaceConfig>(ReplaceConfig::class.java) {

    companion object {
        private const val PROPERTY_NAME_FOR_REPLACING = "servers"

        data class ReplaceConfig(val contextPath: String)
    }

    override fun shortcutFieldOrder(): List<String> {
        return listOf(ReplaceConfig::contextPath.name)
    }

    override fun apply(replaceConfig: ReplaceConfig): GatewayFilter {
        val modifyResponseConfig = ModifyResponseBodyGatewayFilterFactory.Config()
        modifyResponseConfig.setRewriteFunction(Map::class.java, Map::class.java) { _, oldValue: Map<*, *> ->
            @Suppress("UNCHECKED_CAST")
            val castedMap: Map<String, *> = oldValue as Map<String, *>
            Mono.just(oldValue).map {
                val newValue = HashMap<String, Any?>(castedMap)
                newValue.computeIfPresent(PROPERTY_NAME_FOR_REPLACING) { _, _ ->
                    listOf(Server().apply {
                        url = replaceConfig.contextPath
                        description = "URL provided by API gateway"
                    })
                }
                newValue
            }
        }

        return modifyResponseBodyFilterFactory.apply(modifyResponseConfig)
    }


}

