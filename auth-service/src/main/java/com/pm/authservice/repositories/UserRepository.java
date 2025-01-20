package com.pm.authservice.repositories;

import com.pm.authservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE lower(u.email) = lower(:username) OR lower(u.username) = lower(:username)")
    Optional<User> findUserByEmailOrUsername(String username);
}