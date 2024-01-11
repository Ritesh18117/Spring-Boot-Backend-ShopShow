package com.shopshow.backend.services;

import com.shopshow.backend.dao.CartItemsRepository;
import com.shopshow.backend.dao.CustomerRepository;
import com.shopshow.backend.dao.ProductRepository;
import com.shopshow.backend.dao.UserRepository;
import com.shopshow.backend.entities.CartItems;
import com.shopshow.backend.entities.Customer;
import com.shopshow.backend.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class CartItemsService {
    @Autowired
    private CartItemsRepository cartItemsRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<List<CartItems>> getMyCart(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Customer customer = customerRepository.findByUserId(userId);
            List<CartItems> cartItems = cartItemsRepository.findAllByCustomerId(customer.getId());
            return ResponseEntity.of(Optional.of(cartItems));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<CartItems> addToCart(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody CartItems cartItems){
        try{
            Optional<Product> product = productRepository.findById(cartItems.getProduct().getId());
            System.out.println(product.get().getApprovalStatus());
            if(Objects.equals(product.get().getApprovalStatus(), "true")) {
                String token = extractTokenFromHeader(authorizationHeader);
                String username = jwtService.extractUsername(token);
                Long userId = userRepository.findByUsername(username).getId();
                Customer customer = customerRepository.findByUserId(userId);
                cartItems.setCustomer(customer);
                cartItemsRepository.save(cartItems);
                return ResponseEntity.of(Optional.of(cartItems));
            }
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    private String extractTokenFromHeader(String authorizationHeader) {
        // Check if the Authorization header is not null and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token part by removing "Bearer " prefix
            return authorizationHeader.substring(7); // "Bearer ".length() == 7
        }
        return null; // Return null or handle accordingly if token extraction fails
    }

    public ResponseEntity<String> removeCartItem(@RequestHeader(value = "Authorization") String authorizationHeader,@PathVariable Long productId) {
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Customer customer = customerRepository.findByUserId(userId);
            cartItemsRepository.deleteByCustomerIdAndProductId(customer.getId(),productId);
            return ResponseEntity.ok("Item Removed Successfully!!");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    // For testing it's here else move to AdminService
    public ResponseEntity<List<CartItems>> getAllCartItems(){
        try{
            List<CartItems> cartItems = (List<CartItems>) cartItemsRepository.findAll();
            return ResponseEntity.of(Optional.of(cartItems));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<String> addQuantity(Long cartItemId,@RequestHeader(value = "Authorization") String authorizationHeader){
        try {
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Customer customer = customerRepository.findByUserId(userId);

            Optional<CartItems> existingCartItemOptional = cartItemsRepository.findById(cartItemId);
            if (existingCartItemOptional.isPresent()) {
                if(customer.getId() == existingCartItemOptional.get().getCustomer().getId()) {
                    CartItems existingCartItem = existingCartItemOptional.get();
                    existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
                    // Save the updated cart item
                    cartItemsRepository.save(existingCartItem);
                    return ResponseEntity.ok("Updated Successfully!");
                }
                else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }else {
                return ResponseEntity.ok("Cart item not found");
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<String> substractQuantity(Long cartItemId,@RequestHeader(value = "Authorization") String authorizationHeader){
        try {
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Customer customer = customerRepository.findByUserId(userId);

            Optional<CartItems> existingCartItemOptional = cartItemsRepository.findById(cartItemId);
            if (existingCartItemOptional.isPresent()) {
                if(customer.getId() == existingCartItemOptional.get().getCustomer().getId()) {
                    CartItems existingCartItem = existingCartItemOptional.get();
                    if (existingCartItem.getQuantity() == 1) {
                        existingCartItem.setQuantity(existingCartItem.getQuantity() - 1);
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                    }
                    // Save the updated cart item
                    cartItemsRepository.save(existingCartItem);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
                else{
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }else {
                return ResponseEntity.ok("Cart item not found");
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
