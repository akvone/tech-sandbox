package com.akvone.base

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [SecurityConfigurationWhichDisablesIt::class])
annotation class DisableSecurityInTest

@TestConfiguration
@Order(Ordered.HIGHEST_PRECEDENCE)
class SecurityConfigurationWhichDisablesIt : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().antMatchers("/**").permitAll()
    }
}