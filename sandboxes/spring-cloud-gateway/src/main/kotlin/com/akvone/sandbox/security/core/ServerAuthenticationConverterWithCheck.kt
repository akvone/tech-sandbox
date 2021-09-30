package com.akvone.sandbox.security.core

import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.web.server.ServerWebExchange

interface ServerAuthenticationConverterWithCheck : ServerAuthenticationConverter {
    fun isApplicable(exchange: ServerWebExchange): Boolean
}