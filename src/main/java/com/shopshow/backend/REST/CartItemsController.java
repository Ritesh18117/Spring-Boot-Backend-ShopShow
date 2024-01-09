package com.shopshow.backend.REST;

import com.shopshow.backend.entities.CartItems;
import com.shopshow.backend.services.CartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/myCart")
    public ResponseEntity<List<CartItems>> myCart(@RequestHeader(value = "Authorization") String authorizationHeader) {
        return cartItemsService.getMyCart(authorizationHeader);
    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/addToCart")
    public ResponseEntity<CartItems> addToCart(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody CartItems cartItems) {
        return cartItemsService.addToCart(authorizationHeader, cartItems);
    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @DeleteMapping("/removeItem/{product_id}")
    public ResponseEntity<String> removeItem(@RequestHeader(value = "Authorization") String authorizationHeader,@PathVariable Long product_id){
        return cartItemsService.removeCartItem(authorizationHeader,product_id);
    }
}