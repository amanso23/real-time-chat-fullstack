package com.amanso.backend.security;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        Stream<GrantedAuthority> authoritiesStream = Stream.concat(
                new JwtGrantedAuthoritiesConverter().convert(source).stream(),
                extractResourceRoles(source).stream());

        return new JwtAuthenticationToken(source,
                authoritiesStream.collect(Collectors.toSet()));
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        var resourceAccess = new HashMap<>(jwt.getClaim("resource_access"));
        System.out.println("Resource Access: " + resourceAccess);
        var accountObj = resourceAccess.get("account");

        if (accountObj instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, List<String>> eternal = (Map<String, List<String>>) accountObj;
            var roles = eternal.get("roles");
            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.replace("-", "_")))
                    .collect(Collectors.toSet());
        } else {
            throw new IllegalArgumentException("Expected a Map for 'account'");
        }
    }
}