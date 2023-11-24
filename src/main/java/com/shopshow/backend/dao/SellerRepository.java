package com.shopshow.backend.dao;

import com.shopshow.backend.entities.Seller;
import org.springframework.data.repository.CrudRepository;

public interface SellerRepository extends CrudRepository<Seller, Long> {
}
