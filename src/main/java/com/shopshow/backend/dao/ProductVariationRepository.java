package com.shopshow.backend.dao;

import com.shopshow.backend.entities.ProductVariation;
import org.springframework.data.repository.CrudRepository;

public interface ProductVariationRepository extends CrudRepository<ProductVariation,Long> {
    ProductVariation findByProductIdAndColorIdAndSizeId(Long id, Long colorId, Long product_id);
//    List<ProductVariation> findAllByProductId
}
