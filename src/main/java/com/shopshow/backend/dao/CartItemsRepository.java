package com.shopshow.backend.dao;

import com.shopshow.backend.entities.CartItems;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemsRepository extends CrudRepository<CartItems,Long> {
    List findAllByCustomerId(Long customer_id);
}
