package com.shopshow.backend.dao;

import com.shopshow.backend.entities.CartItems;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartItemsRepository extends CrudRepository<CartItems,Long> {
    List<CartItems> findAllByCustomerId(Long customer_id);
    List<CartItems> findAllByCustomer_IdAndProduct_Id(Long customerId, Long productId);

    @Transactional
    default void deleteByCustomerIdAndProductId(Long customerId, Long productId) {
        List<CartItems> cartItemsToDelete = findAllByCustomer_IdAndProduct_Id(customerId, productId);
        cartItemsToDelete.forEach(this::delete);
    }
}
