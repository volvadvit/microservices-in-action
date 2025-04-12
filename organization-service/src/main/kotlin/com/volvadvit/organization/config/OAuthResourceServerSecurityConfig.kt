package com.volvadvit.organization.config

import com.volvadvit.organization.oauth.converter.KeycloakJwtAuthenticationConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true) // To enable RolesAllowed
class OAuthResourceServerSecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .authorizeHttpRequests { authorize ->
                authorize
//                    .anyRequest().permitAll()
                    .requestMatchers("/actuator/**").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/v1/**").hasRole("ROLE_ADMIN")
//                    .requestMatchers(HttpMethod.POST, "/v1/**").hasAnyAuthority("USER", "ADMIN")
                    .anyRequest().authenticated()
//                    .anyRequest().denyAll()
            }
            .oauth2ResourceServer { oauth2: OAuth2ResourceServerConfigurer<HttpSecurity?> ->
                oauth2.jwt { jwtCustomizer ->
                    jwtCustomizer.jwtAuthenticationConverter(KeycloakJwtAuthenticationConverter())
                }
            }
//            .oauth2ResourceServer { oauth2 -> oauth2.jwt(Customizer.withDefaults()) }
            .csrf { obj: CsrfConfigurer<HttpSecurity> -> obj.disable() }
            .sessionManagement { sessionManagement: SessionManagementConfigurer<HttpSecurity?> ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .build()
    }

}