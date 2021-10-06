package com.akvone.sandbox.security.custom

import org.springframework.security.web.server.authentication.AuthenticationWebFilter

class CustomAuthenticationWebFilter : AuthenticationWebFilter(
    CustomReactiveAuthenticationManager()
)

