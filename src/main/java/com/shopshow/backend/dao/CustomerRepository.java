package com.shopshow.backend.dao;

import com.shopshow.backend.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
}
