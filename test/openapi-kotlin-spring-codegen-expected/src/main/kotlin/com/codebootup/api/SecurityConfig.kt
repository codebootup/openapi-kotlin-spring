package com.codebootup.api

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
open class SecurityConfig {

    @Bean
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http.csrf().disable()
            .httpBasic()
            .and()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "/api/v1/examples")
            .authenticated()
            .and()
            .authorizeHttpRequests().anyRequest()
            .permitAll()
        return http.build()
    }
}
