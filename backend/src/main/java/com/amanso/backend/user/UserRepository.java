package com.amanso.backend.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(name = UserConstants.FIND_USER_BY_EMAIL)
    Optional<User> findByEmail(String email);

    @Query(name = UserConstants.FIND_USER_BY_PUBLIC_ID)
    Optional<User> findByPublicId(UUID publicId);

    @Query(name = UserConstants.FIND_ALL_USERS_EXCEPT_SELF)
    Optional<User> findAllUsersExceptSelf(UUID publicId);

}
