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

import java.util.*;

@Component
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductVariationRepository productVariationRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Product> addProduct(@RequestBody Product product,@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Seller seller = sellerRepository.findByUserId(userId);
            product.setSeller(seller);
            product.setApprovalStatus("false");
            if(Objects.equals(seller.getApprovalStatus(), "true")){
                productRepository.save(product);
                product.setSeller(null);
                return ResponseEntity.of(Optional.of(product));
            }
            else{
                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
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

    public ResponseEntity<List<Product>> myProducts(@RequestHeader(value = "Authorization") String authorizationHeader) {
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            Seller seller = sellerRepository.findByUserId(userId);
            List<Product> products = productRepository.findBySeller_Id(seller.getId());
            for(Product product : products){
                product.setSeller(null);
            }
            return ResponseEntity.of(Optional.of(products));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<ArrayList> approvedProduct(){
        try{
            List<ProductVariation> productVariations = (List<ProductVariation>) productVariationRepository.findAll();

            Map<String, List<String>> groupedData = new HashMap<>();

            for (ProductVariation pv : productVariations) {
                String key = pv.getColor().getColor() + "_" + pv.getProduct().getId();

                if (!groupedData.containsKey(key)) {
                    groupedData.put(key, new ArrayList<>());
                }

                List<String> sizeQuanList = groupedData.get(key);
                sizeQuanList.add(pv.getSize().getSize() + ", " + pv.getQuantity());
            }

            // Convert to JSON
            List<Map<String, Object>> outputList = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : groupedData.entrySet()) {
                Map<String, Object> productGroup = new HashMap<>();
                String[] keyParts = entry.getKey().split("_");

                Optional<Product> product = productRepository.findById(Long.parseLong(keyParts[1]));
                if(product.isPresent() && Objects.equals(product.get().getApprovalStatus(), "true")){
                    productGroup.put("color", keyParts[0]);
                    product.get().setSeller(null);
                    product.get().setMargin(null);
                    productGroup.put("product", product);
                    productGroup.put("size_quan", entry.getValue());

                    outputList.add(productGroup);
                }else{
                    continue;
                }
            }
            return ResponseEntity.of(Optional.of((ArrayList) outputList));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //For testing here Otherwise move to Admin Service
    public ResponseEntity<List<Product>> getAllProduct(){
        try{
            List<Product> products = (List<Product>) productRepository.findAll();
            if(products.size() <= 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.of(Optional.of(products));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
