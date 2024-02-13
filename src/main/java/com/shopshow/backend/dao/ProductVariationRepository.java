package com.shopshow.backend.dao;

import com.shopshow.backend.entities.ProductVariation;
import org.springframework.data.repository.CrudRepository;

public interface ProductVariationRepository extends CrudRepository<ProductVariation,Long> {
    ProductVariation findByProductIdAndSize(long product_id,String size);
}
