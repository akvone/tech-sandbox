package com.akvone.sandbox.security.custom

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

@Service
class CustomExternalAuthenticationSystemService {

    private val executorService = Executors.newFixedThreadPool(8)

    fun authenticate(exchange: ServerWebExchange): Mono<Authentication> {
        val completableFuture: CompletableFuture<CustomAuthentication> =
            CompletableFuture.supplyAsync({ authenticatePrivate(exchange) }, executorService)
        val mono: Mono<Authentication> = Mono.fromFuture(completableFuture)
        return mono.onErrorResume({ it is AccessDeniedException }, { Mono.empty() })
    }

    private fun authenticatePrivate(exchange: ServerWebExchange): CustomAuthentication {
        Thread.sleep(1000) // Imitate blocking IO
        val headerValue: String? = exchange.request.headers.getFirst("Custom-Auth")

        return if (headerValue != null && headerValue.isNotEmpty()) {
            CustomAuthentication(headerValue)
        } else {
            throw AccessDeniedException("Not authenticated")
        }
    }
}