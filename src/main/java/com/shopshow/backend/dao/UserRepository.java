package com.shopshow.backend.dao;

import com.shopshow.backend.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    Optional<String> findRoleByUsername(String username);
}