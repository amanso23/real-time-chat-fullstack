package com.amanso.backend.user;

import java.util.Optional;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSynchronizer {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void synchronizeWithIdentityProvider(Jwt token) {
        log.info("Synchronizing user with identity provider (Keycloak)");
        getUserEmail(token).ifPresent(email -> {
            log.info("Synchronizing user with email: {}", email);
            Optional<User> optionalUser = userRepository.findByEmail(email);
            User user = userMapper.fromTokenAttributes(token.getClaims());

        });
    }

    private Optional<String> getUserEmail(Jwt token) {
        return Optional.ofNullable((String) token.getClaims().get("email"));
    }

}
