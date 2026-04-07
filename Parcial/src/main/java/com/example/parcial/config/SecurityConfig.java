package com.example.parcial.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Configuración de seguridad del proyecto
// Acá se define que todos los endpoints necesitan un token JWT válido de Keycloak
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Habilita la comprobación de roles en los controladores
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Como es una API REST, no necesitamos CSRF
                .csrf(csrf -> csrf.disable())

                // Todas las peticiones a /api/** necesitan autenticación
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())

                // La sesión es stateless, no guardamos estado en el servidor
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Validamos los tokens JWT que vienen de Keycloak y le decimos cómo leer los
                // roles
                .oauth2ResourceServer(
                        oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }

    // Bean encargado de extraer los roles desde el JSON de Keycloak (realm_access
    // -> roles)
    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
            if (realmAccess == null || !realmAccess.containsKey("roles")) {
                return List.of();
            }
            List<String> roles = (List<String>) realmAccess.get("roles");
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                    .collect(Collectors.toList());
        });
        return converter;
    }
}
