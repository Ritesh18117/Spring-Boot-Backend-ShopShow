package com.shopshow.backend.services;

import com.shopshow.backend.dao.*;
import com.shopshow.backend.dto.CartItemRequest;
import com.shopshow.backend.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
    @Autowired
    private  ColorRepository colorRepository;
    @Autowired
    private  SizeRepository sizeRepository;
    @Autowired
    private ProductVariationRepository productVariationRepository;

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
    public ResponseEntity<CartItems> addToCart(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody CartItemRequest cartItemRequest){
        try{
            Color color = colorRepository.findByColor(cartItemRequest.getColor());
            Size size = sizeRepository.findBySize(cartItemRequest.getSize());
            ProductVariation productVariation = productVariationRepository.findByProductIdAndColorIdAndSizeId(cartItemRequest.getProduct_id(),color.getId(),size.getId());
            Optional<Product> product = productRepository.findById(cartItemRequest.getProduct_id());
            System.out.println(product.get().getApprovalStatus());
            if(Objects.equals(product.get().getApprovalStatus(), "true") && productVariation != null) {
                String token = extractTokenFromHeader(authorizationHeader);
                String username = jwtService.extractUsername(token);
                Long userId = userRepository.findByUsername(username).getId();
                Customer customer = customerRepository.findByUserId(userId);

                CartItems cartItems = new CartItems();
                cartItems.setCustomer(customer);
                cartItems.setProduct(product.get());
                cartItems.setProductVariation(productVariation);
                cartItems.setQuantity(cartItemRequest.getQuantity());
                cartItems.setAddedDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
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

    public ResponseEntity<String> removeCartItem(@RequestHeader(value = "Authorization") String authorizationHeader, Long productVariationId) {
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Customer customer = customerRepository.findByUserId(userId);
            System.out.println(customer.getId());
            System.out.println(cartItemsRepository.findByCustomerIdAndProductVariationId(customer.getId(),productVariationId).getId());
            Long cartItemId = cartItemsRepository.findByCustomerIdAndProductVariationId(customer.getId(),productVariationId).getId();
            cartItemsRepository.deleteById(cartItemId);
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
