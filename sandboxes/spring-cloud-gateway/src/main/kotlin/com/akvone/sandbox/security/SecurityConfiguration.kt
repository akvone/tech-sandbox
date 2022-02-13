package com.akvone.sandbox.security

import com.akvone.sandbox.security.combined.CombinedReactiveAuthorizationManager
import com.akvone.sandbox.security.custom.CustomAuthenticationWebFilter
import com.akvone.sandbox.security.custom.CustomServerAuthenticationConverter
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
class SecurityConfiguration {
    @Bean
    fun springSecurityFilterChain(
        http: ServerHttpSecurity,
        customServerAuthenticationConverter: CustomServerAuthenticationConverter,
    ): SecurityWebFilterChain? {
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

        return http.build()
    }

}
