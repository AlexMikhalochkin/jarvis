package com.am.jarvis

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
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
            .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests { request ->
                request.requestMatchers("/yandex/v1.0/**", "/smartthings")
                    .permitAll()
            }
            .build()
    }
}
