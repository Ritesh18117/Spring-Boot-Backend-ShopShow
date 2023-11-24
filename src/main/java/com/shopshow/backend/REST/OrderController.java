package com.shopshow.backend.REST;

import com.shopshow.backend.entities.Order;
import com.shopshow.backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/test")
    public String test(){
        return "This is test from Order Controller!";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Order>> getAllOrder(){
        return orderService.getAllOrder();
    }

    @PostMapping("/addOrder")
    public ResponseEntity<Order> addOrder(@RequestBody Order order){
        return orderService.addOrder(order);
    }
}
