package com.akvone.sandbox.security

import com.akvone.sandbox.security.combined.CombinedReactiveAuthorizationManager
import com.akvone.sandbox.security.custom.CustomAuthenticationWebFilter
import com.akvone.sandbox.security.custom.CustomServerAuthenticationConverter
import com.akvone.sandbox.swagger.AddSwaggerUrlsBeanPostProcessor.Companion.generateSwaggerProxyRootPath
import com.akvone.sandbox.swagger.AggregatingSwaggerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers

@Configuration
@EnableWebFluxSecurity
class SecurityConfiguration(
    private val customServerAuthenticationConverter: CustomServerAuthenticationConverter,
    private val swaggerProperties: AggregatingSwaggerProperties
) {
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        http.httpBasic()
        http.formLogin().disable()
        http.logout().disable()
        http.csrf().disable()

        val customAuthenticationWebFilter = CustomAuthenticationWebFilter()
        customAuthenticationWebFilter.setServerAuthenticationConverter(customServerAuthenticationConverter)
        http.addFilterAt(customAuthenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        val combinedManager = CombinedReactiveAuthorizationManager()

        http.authorizeExchange().matchers(NegatedServerWebExchangeMatcher(ServerWebExchangeMatchers.pathMatchers("/actuator/*"))).access(combinedManager)
        http.authorizeExchange().pathMatchers("/actuator/*").permitAll()
        http.configureSecurityForSwagger()

        return http.build()
    }

    private fun ServerHttpSecurity.configureSecurityForSwagger() {
        authorizeExchange().pathMatchers("/swagger-ui.html").permitAll()
        authorizeExchange().pathMatchers("/webjars/swagger-ui/*").permitAll()
        authorizeExchange().pathMatchers("/v3/api-docs/swagger-config").permitAll()
        swaggerProperties.routes.forEach {
            authorizeExchange().pathMatchers("${generateSwaggerProxyRootPath(it.key)}/v3/api-docs").permitAll()
        }
    }

}
