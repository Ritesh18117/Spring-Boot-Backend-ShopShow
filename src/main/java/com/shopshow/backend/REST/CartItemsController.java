package com.shopshow.backend.REST;

import com.shopshow.backend.entities.CartItems;
import com.shopshow.backend.services.CartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartItemsController {

    @Autowired
    private CartItemsService cartItemsService;

    @GetMapping("/test")
    public String test() {
        return "This is test from Cart";
    }

    @GetMapping("/myCart")
    public ResponseEntity<List<CartItems>> myCart(@RequestHeader(value = "Authorization") String authorizationHeader) {
        return cartItemsService.getMyCart(authorizationHeader);
    }

    @PostMapping("/addToCart")
    public ResponseEntity<CartItems> addToCart(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody CartItems cartItems) {
        return cartItemsService.addToCart(authorizationHeader, cartItems);
    }
}