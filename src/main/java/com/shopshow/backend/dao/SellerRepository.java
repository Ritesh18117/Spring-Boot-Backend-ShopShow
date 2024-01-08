package com.shopshow.backend.dao;

import com.shopshow.backend.entities.Seller;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SellerRepository extends CrudRepository<Seller, Long> {
    Optional<Seller> findByUserId(Long userId);
}
