package com.akvone.sandbox.security.combined

import com.akvone.sandbox.security.basic.UserDetailsWithRequestRestrictions
import com.akvone.sandbox.security.custom.CustomAuthentication
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.ReactiveAuthorizationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authorization.AuthorizationContext
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class CombinedReactiveAuthorizationManager : ReactiveAuthorizationManager<AuthorizationContext> {

    override fun check(
        authentication: Mono<Authentication>,
        context: AuthorizationContext
    ): Mono<AuthorizationDecision> {
        return authentication.flatMap {
            when (it) {
                is UsernamePasswordAuthenticationToken -> {
                    val granted = validate(it, context.exchange)
                    Mono.just(AuthorizationDecision(granted))
                }
                is CustomAuthentication -> {
                    val granted = validate(it, context.exchange)
                    Mono.just(AuthorizationDecision(granted))
                }
                else -> {
                    Mono.empty()
                }
            }
        }
    }

    private fun validate(
        authentication: CustomAuthentication,
        exchange: ServerWebExchange
    ): Boolean {
        return true // Replace with something meaningful
    }

    private fun validate(
        authentication: UsernamePasswordAuthenticationToken,
        exchange: ServerWebExchange
    ): Boolean {
        val principal = authentication.principal
        if (principal is UserDetailsWithRequestRestrictions) {
            if (principal.username == "sandbox") return true

            val expectedPath = principal.path
            val expectedMethod = principal.method

            val actualPath = exchange.request.path.value()
            val actualMethod = exchange.request.method

            return actualPath == expectedPath && actualMethod == expectedMethod
        } else {
            throw AccessDeniedException("Principal is incorrect for authorization")
        }
    }

}