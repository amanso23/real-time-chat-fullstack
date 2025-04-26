package com.amanso.backend.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .cors(Customizer.withDefaults())
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(req -> req
                                                .requestMatchers(
                                                                "/swagger-ui/**",
                                                                "/v3/api-docs/**",
                                                                "/swagger-resources/**",
                                                                "/webjars/**",
                                                                "/ws/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .oauth2ResourceServer(auth -> auth
                                                .jwt(token -> token.jwtAuthenticationConverter(
                                                                new KeycloakJwtAuthenticationConverter())));
                return http.build();
        }

        @Bean
        public CorsFilter corsFilter() {
                final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                final CorsConfiguration config = new CorsConfiguration();
                config.setAllowCredentials(true);
                config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                config.setAllowedHeaders(Arrays.asList(
                                HttpHeaders.ORIGIN,
                                HttpHeaders.CONTENT_TYPE,
                                HttpHeaders.ACCEPT,
                                HttpHeaders.AUTHORIZATION

                ));

                config.setAllowedMethods(Arrays.asList(
                                "GET",
                                "POST",
                                "PUT",
                                "DELETE",
                                "OPTIONS",
                                "PATCH"));

                source.registerCorsConfiguration("/**", config);
                return new CorsFilter(source);
        }

}
