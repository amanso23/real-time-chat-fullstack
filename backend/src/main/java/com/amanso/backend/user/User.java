package com.amanso.backend.user;

import java.time.LocalDateTime;

import com.amanso.backend.common.BaseAuditingEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseAuditingEntity {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime lastSeen;

}
