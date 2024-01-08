package com.shopshow.backend.services;

import com.shopshow.backend.dao.SellerRepository;
import com.shopshow.backend.dao.UserRepository;
import com.shopshow.backend.entities.Seller;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@Component
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

//    public ResponseEntity<List<Seller>> getAllSeller(){
//        try{
//            List<Seller> list = (List<Seller>) sellerRepository.findAll();
//            if(list.size() <= 0){
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            }
//            return ResponseEntity.of(Optional.of(list));
//        } catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }


    public ResponseEntity<Seller> newSeller(@RequestBody Seller seller){
        try{
            sellerRepository.save(seller);
            return ResponseEntity.of(Optional.of(seller));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Optional<Seller>> getSellerById(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            System.out.println(username);
            Long userId = userRepository.findByUsername(username).get().getId();
            System.out.println("Seller Id from id route : " + userId);
            Optional<Seller> seller =sellerRepository.findByUserId(userId);
            return ResponseEntity.of(Optional.of(seller));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Seller> updateSellerProfile(@RequestBody Seller updatedSeller,@RequestHeader(value = "Authorization") String authorizationHeader){
        try {
            Optional<Seller> optionalSeller = getSellerById(authorizationHeader).getBody();
            if (optionalSeller.isPresent()) {
                Seller existingSeller = optionalSeller.get();
                // Copy updated fields from updatedSeller to existingSeller
                BeanUtils.copyProperties(updatedSeller, existingSeller, "id");
                Seller savedSeller = sellerRepository.save(existingSeller);
                return ResponseEntity.of(Optional.of(savedSeller));
            } else {
                return ResponseEntity.notFound().build();
            }
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

}
