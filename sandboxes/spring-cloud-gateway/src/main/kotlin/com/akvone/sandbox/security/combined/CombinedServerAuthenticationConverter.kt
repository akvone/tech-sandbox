package com.akvone.sandbox.security.combined

import com.akvone.sandbox.security.basic.ServerHttpBasicAuthenticationConverterWithCheck
import com.akvone.sandbox.security.custom.CustomServerAuthenticationConverter
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
class CombinedServerAuthenticationConverter(
    val serverHttpBasicAuthenticationConverter: ServerHttpBasicAuthenticationConverterWithCheck,
    val customServerAuthenticationConverter: CustomServerAuthenticationConverter
) : ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        return if (customServerAuthenticationConverter.isApplicable(exchange)) {
            customServerAuthenticationConverter.convert(exchange)
        } else if (serverHttpBasicAuthenticationConverter.isApplicable(exchange)) {
            serverHttpBasicAuthenticationConverter.convert(exchange)
        } else {
            Mono.empty()
        }
    }

}