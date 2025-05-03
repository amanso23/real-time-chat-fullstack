package com.amanso.backend.user;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime lastSeen;
    private String state;
    private boolean isOnline; // This field is not stored in the database, but is calculated based on the
                              // lastSeen field.

}
