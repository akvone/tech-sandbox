package com.akvone.sandbox.security.custom

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.core.Authentication
import reactor.core.publisher.Mono

/**
 * Don't make it @Component. It will replace the default one
 */
class CustomReactiveAuthenticationManager : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        authentication.isAuthenticated = true
        return Mono.just(authentication)
    }

}