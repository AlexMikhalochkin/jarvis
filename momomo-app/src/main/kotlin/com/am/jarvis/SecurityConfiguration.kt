package com.am.jarvis

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

/**
 * Security configuration.
 *
 * @author Alex Mikhalochkin
 */
@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.csrf { it.disable() }
            .authorizeHttpRequests { request ->
                request.requestMatchers("/yandex/v1.0/**", "/smartthings")
                    .permitAll()
            }
            .build()
    }

    @Bean
    fun noopAuthenticationManager(): AuthenticationManager {
        return AuthenticationManager { _ -> throw AuthenticationServiceException("Authentication is disabled") }
    }
}
