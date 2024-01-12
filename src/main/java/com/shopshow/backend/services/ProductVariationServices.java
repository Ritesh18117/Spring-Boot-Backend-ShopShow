package com.shopshow.backend.services;

import com.shopshow.backend.dao.ProductRepository;
import com.shopshow.backend.dao.ProductVariationRepository;
import com.shopshow.backend.dao.SellerRepository;
import com.shopshow.backend.dao.UserRepository;
import com.shopshow.backend.entities.Product;
import com.shopshow.backend.entities.ProductVariation;
import com.shopshow.backend.entities.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ProductVariationServices {
    @Autowired
    private ProductVariationRepository productVariationRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<List<ProductVariation>> getAllProductVariation(){
        try{
            List<ProductVariation> productVariations = (List<ProductVariation>) productVariationRepository.findAll();
            if(productVariations.size() <= 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.of(Optional.of(productVariations));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<ProductVariation> addProductVariation(@RequestHeader(value = "Authorization") String authorizationHeader,@RequestBody ProductVariation productVariation){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            System.out.println(username);
            Long userId = userRepository.findByUsername(username).getId();
            Seller seller = sellerRepository.findByUserId(userId);
            Optional<Product> product = productRepository.findById(productVariation.getProduct().getId());
            if (Objects.equals(seller.getId(), product.get().getSeller().getId())){
                if(Objects.equals(product.get().getApprovalStatus(), "true")){
                    productVariationRepository.save(productVariation);
                    return ResponseEntity.of(Optional.of(productVariation));
                }
                else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Optional<ProductVariation>> updateProductVariation(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody ProductVariation updatedproductVariation){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            System.out.println(username);
            Long userId = userRepository.findByUsername(username).getId();
            Seller seller = sellerRepository.findByUserId(userId);
            Optional<Product> product = productRepository.findById(updatedproductVariation.getProduct().getId());
            if (seller.getId() == product.get().getSeller().getId()){
                if(Objects.equals(product.get().getApprovalStatus(), "true")){
                    Optional<ProductVariation> existingProductVariation = productVariationRepository.findById(updatedproductVariation.getId());
                    existingProductVariation.get().setQuantity(updatedproductVariation.getQuantity());
                    productVariationRepository.save(existingProductVariation.get());
                    return ResponseEntity.of(Optional.of(existingProductVariation));
                }
                else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e){
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
}
