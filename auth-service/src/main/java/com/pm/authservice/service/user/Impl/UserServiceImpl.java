package com.pm.authservice.service.user.Impl;

import com.pm.authservice.entities.User;
import com.pm.authservice.exceptions.BadRequestException;
import com.pm.authservice.repositories.UserRepository;
import com.pm.authservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByEmailOrUsername(String username) {
        return this.userRepository.findUserByEmailOrUsername(username).orElseThrow(() -> {
            log.error("no user available against given username or email: {}", username);
            return new BadRequestException("no user available against given username or email: " + username);
        });
    }
}