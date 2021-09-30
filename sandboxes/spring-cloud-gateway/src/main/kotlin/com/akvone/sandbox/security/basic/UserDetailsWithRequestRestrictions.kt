package com.akvone.sandbox.security.basic

import org.springframework.http.HttpMethod
import org.springframework.security.core.userdetails.UserDetails

data class UserDetailsWithRequestRestrictions(
    val userDetails: UserDetails,
    val path: String,
    val method: HttpMethod
) : UserDetails by userDetails