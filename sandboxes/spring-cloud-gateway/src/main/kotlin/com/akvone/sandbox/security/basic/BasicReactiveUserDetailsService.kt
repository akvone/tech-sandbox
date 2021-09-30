package com.akvone.sandbox.security.basic

import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

@Component
class BasicReactiveUserDetailsService : ReactiveUserDetailsService {
    private val users: ConcurrentMap<String, UserDetailsWithRequestRestrictions> = ConcurrentHashMap()

    override fun findByUsername(username: String): Mono<UserDetails> {
        return users[username]
            ?.let { Mono.just(it as UserDetails).log() } // TODO: Check if we can make cast
            ?: Mono.empty()
    }

    fun update(newValues: Map<String, UserDetailsWithRequestRestrictions>) {
        newValues.forEach {
            users[it.key] = it.value
        }
    }
}


