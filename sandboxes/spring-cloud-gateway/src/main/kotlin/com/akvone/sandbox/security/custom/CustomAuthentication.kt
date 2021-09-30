package com.akvone.sandbox.security.custom

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class CustomAuthentication(
    private val principal: String,
    authorities: Collection<GrantedAuthority>
) : AbstractAuthenticationToken(emptyList()) {

    constructor(principal: String) : this(principal, emptyList())

    override fun getCredentials(): Any {
        return "CREDENTIALS"
    }

    override fun getPrincipal(): Any {
        return principal
    }

}