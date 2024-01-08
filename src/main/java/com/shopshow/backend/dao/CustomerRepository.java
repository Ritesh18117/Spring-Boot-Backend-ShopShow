package com.shopshow.backend.dao;

import com.shopshow.backend.entities.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
    Optional<Customer> findByUserId(Long userId);
}
