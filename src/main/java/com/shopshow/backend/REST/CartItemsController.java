package com.shopshow.backend.REST;

import com.shopshow.backend.dto.CartItemRequest;
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
    public ResponseEntity<CartItems> addToCart(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody CartItemRequest cartItemRequest) {
        return cartItemsService.addToCart(authorizationHeader, cartItemRequest);
    }

//    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
//    @DeleteMapping("/removeItem/{productVariation_id}")
//    public ResponseEntity<String> removeItem(@RequestHeader(value = "Authorization") String authorizationHeader,@PathVariable Long productVariation_id){
//        return cartItemsService.removeCartItem(authorizationHeader,productVariation_id);
//    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/addQuantity/{cartItemId}")
    public ResponseEntity<String> addQuantity(@PathVariable Long id,@RequestHeader(value = "Authorization") String authorizationHeader) {
        return cartItemsService.addQuantity(id,authorizationHeader);
    }
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @PostMapping("/substarctQuantity/{cartItemId}")
    public ResponseEntity<String> substarctQuantity(@PathVariable Long id,@RequestHeader(value = "Authorization") String authorizationHeader) {
        return cartItemsService.substractQuantity(id,authorizationHeader);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<CartItems>> getAllCartItems(){
        return cartItemsService.getAllCartItems();
    }
}