package com.akvone.sandbox.filters

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange

/**
 * Throws 403 if request contains "allowed=false" query param
 */
@Component
class SecurityGatewayFilterFactory : AbstractGatewayFilterFactory<Any>(Any::class.java) {
    private val httpStatusIfNotAllowed = HttpStatus.FORBIDDEN

    override fun apply(config: Any): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            return@GatewayFilter if (isAllowed(exchange)) {
                chain.filter(exchange)
            } else {
                ServerWebExchangeUtils.setResponseStatus(exchange, httpStatusIfNotAllowed)
                exchange.response.setComplete()
            }
        }
    }

    private fun isAllowed(exchange: ServerWebExchange): Boolean {
        val queryParam = exchange.request.queryParams.getFirst("allowed")
        return queryParam != "false"
    }

}
