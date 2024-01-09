package com.shopshow.backend.dao;

import com.shopshow.backend.entities.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address,Long> {
    List<Address> getAddressesByCustomerId(Long Customer_id);
}
