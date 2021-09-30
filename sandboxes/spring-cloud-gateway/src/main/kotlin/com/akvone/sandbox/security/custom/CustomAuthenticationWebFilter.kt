package com.akvone.sandbox.security.custom

import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationWebFilter : AuthenticationWebFilter(
    CustomReactiveAuthenticationManager()
)

