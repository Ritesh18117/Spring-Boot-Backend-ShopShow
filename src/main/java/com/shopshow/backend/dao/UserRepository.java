package com.shopshow.backend.dao;

import com.shopshow.backend.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}