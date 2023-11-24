package com.shopshow.backend.dao;

import com.shopshow.backend.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address,Long> {
}
