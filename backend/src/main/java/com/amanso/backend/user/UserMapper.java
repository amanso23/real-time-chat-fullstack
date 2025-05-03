package com.amanso.backend.user;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

/*
* Return a User object from the token attributes. The token attributes are the claims
* returned by the identity provider (Keycloak). The User object is the entity that is
* stored in the database.
*/
@Service
public class UserMapper {
    public User fromTokenAttributes(Map<String, Object> attributes) {
        User user = new User();

        if (attributes.containsKey("sub")) {
            user.setId(UUID.fromString((String) attributes.get("sub")));
        }

        if (attributes.containsKey("given_name")) {
            user.setFirstName((String) attributes.get("given_name"));
        } else if (attributes.containsKey("nickname")) {
            user.setFirstName((String) attributes.get("nickname"));
        }

        if (attributes.containsKey("family_name")) {
            user.setLastName((String) attributes.get("family_name"));
        }

        if (attributes.containsKey("email")) {
            user.setEmail((String) attributes.get("email"));
        }

        // Set the last seen time to the current time
        user.setLastSeen(LocalDateTime.now());

        return user;

    }

    public void updateUserFromAttributes(User user, Map<String, Object> attributes) {
        if (attributes.containsKey("given_name")) {
            user.setFirstName((String) attributes.get("given_name"));
        } else if (attributes.containsKey("nickname")) {
            user.setFirstName((String) attributes.get("nickname"));
        }

        if (attributes.containsKey("family_name")) {
            user.setLastName((String) attributes.get("family_name"));
        }

        if (attributes.containsKey("email")) {
            user.setEmail((String) attributes.get("email"));
        }

        user.setLastSeen(LocalDateTime.now());
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .lastSeen(user.getLastSeen())
                .isOnline(user.isOnline())
                .build();

    }
}
