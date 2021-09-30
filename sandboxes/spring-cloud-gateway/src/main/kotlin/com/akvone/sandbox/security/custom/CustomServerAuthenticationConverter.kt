package com.akvone.sandbox.security.custom

import com.akvone.sandbox.security.core.ServerAuthenticationConverterWithCheck
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class CustomServerAuthenticationConverter(
    val customExternalAuthenticationSystemService: CustomExternalAuthenticationSystemService
) : ServerAuthenticationConverterWithCheck {
    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        return if (isApplicable(exchange)) {
            customExternalAuthenticationSystemService.authenticate(exchange)
        } else {
            Mono.empty()
        }
    }

    override fun isApplicable(exchange: ServerWebExchange): Boolean {
        val headerValue = exchange.request.headers.getFirst("Custom-Auth")
        return headerValue != null
    }

}