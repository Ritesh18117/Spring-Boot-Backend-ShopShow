package com.shopshow.backend.dao;

import com.shopshow.backend.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product,Long> {

}
