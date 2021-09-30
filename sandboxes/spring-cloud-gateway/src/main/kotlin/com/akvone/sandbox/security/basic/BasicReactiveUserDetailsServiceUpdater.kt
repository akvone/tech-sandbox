package com.akvone.sandbox.security.basic

import org.springframework.http.HttpMethod
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component

@Component
class BasicReactiveUserDetailsServiceUpdater(
    val basicReactiveUserDetailsService: BasicReactiveUserDetailsService,
) {

    @Scheduled(fixedRate = 10_000)
    fun update() { // Replace with something meaningful
        basicReactiveUserDetailsService.update(getCurrentBasicAuthUsersDetails())
    }

    private fun getCurrentBasicAuthUsersDetails(): Map<String, UserDetailsWithRequestRestrictions> = mapOf(
        generateUserPair("first"),
        generateUserPair("second"),
    )

    private fun generateUserPair(username: String): Pair<String, UserDetailsWithRequestRestrictions> {
        return username to UserDetailsWithRequestRestrictions(
            User.withDefaultPasswordEncoder()
                .username(username)
                .password("$username-password")
                .roles(BASIC_AUTHORITY_PREFIX)
                .build(),
            "/$username-path",
            HttpMethod.GET
        )
    }

    companion object {
        private const val BASIC_AUTHORITY_PREFIX = "BASIC"
    }
}