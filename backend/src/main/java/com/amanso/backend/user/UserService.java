package com.amanso.backend.user;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> findAllUsersExceptSelf(Authentication authentication) {
        UUID publicId = UUID.fromString(authentication.getName());
        return userRepository.findAllUsersExceptSelf(publicId)
                .stream()
                .map(userMapper::toUserResponse)
                .toList();

    }
}
