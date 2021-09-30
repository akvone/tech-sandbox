package com.akvone.sandbox.security.basic

import com.akvone.sandbox.security.core.ServerAuthenticationConverterWithCheck
import org.springframework.http.HttpHeaders
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerHttpBasicAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class ServerHttpBasicAuthenticationConverterWithCheck : ServerAuthenticationConverterWithCheck {
    private val serverHttpBasicAuthenticationConverter = ServerHttpBasicAuthenticationConverter()

    override fun isApplicable(exchange: ServerWebExchange): Boolean {
        val request = exchange.request
        val authorization = request.headers.getFirst(HttpHeaders.AUTHORIZATION)
        return StringUtils.startsWithIgnoreCase(authorization, "basic ")
    }

    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        return serverHttpBasicAuthenticationConverter.convert(exchange)
    }
}