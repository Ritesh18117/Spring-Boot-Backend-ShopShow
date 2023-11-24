package com.shopshow.backend.services;

import com.shopshow.backend.dao.OrderRepository;
import com.shopshow.backend.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public ResponseEntity<List<Order>> getAllOrder(){
        try{
            List<Order> orders = (List<Order>) orderRepository.findAll();
            if (orders.size() <= 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(orders));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Order> addOrder(@RequestBody Order order){
        try{
            orderRepository.save(order);
            return ResponseEntity.of(Optional.of(order));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

