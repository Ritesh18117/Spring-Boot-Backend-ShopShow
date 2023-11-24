package com.shopshow.backend.dao;

import com.shopshow.backend.entities.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,Long> {
}
