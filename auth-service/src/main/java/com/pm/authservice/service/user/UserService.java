package com.pm.authservice.service.user;

import com.pm.authservice.entities.User;
import jakarta.validation.constraints.NotEmpty;

public interface UserService {

    User findByEmailOrUsername(String username);
}