package com.amanso.backend.user;

import java.util.Optional;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * This class is responsible for synchronizing the user with the identity provider (Keycloak).
 * Get the claims from the given token and check if the user exists in the database by email.
 * If the user exists call the userMapper who will return a User object from the token attributes.
 */

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

            // Update the user with the new attributes from the token if it exists
            // If it doesn't exist, create a new user with the attributes from the token
            optionalUser.ifPresentOrElse(
                    existingUser -> {
                        user.setId(existingUser.getId());
                        userRepository.save(user);
                        log.info("Updated existing user with ID: {}", existingUser.getId());
                    },
                    () -> {
                        userRepository.save(user);
                        log.info("Created new user with email: {}", email);
                    });
        });
    }

    private Optional<String> getUserEmail(Jwt token) {
        return Optional.ofNullable((String) token.getClaims().get("email"));
    }

}
