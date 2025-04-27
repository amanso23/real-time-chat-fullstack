// package com.amanso.backend.user;

// import java.util.Map;

// import org.springframework.stereotype.Service;

// /*
// * Return a User object from the token attributes. The token attributes are
// the claims
// * returned by the identity provider (Keycloak). The User object is the entity
// that is
// * stored in the database.
// */
// @Service
// public class UserMapper {
// public User fromTokenAttributes(Map<String, Object> attributes) {
// User user = new User();

// if (attributes.containsKey("sub")) {
// user.setId(Long.valueOf((String) attributes.get("sub")));
// }

// }

// }
