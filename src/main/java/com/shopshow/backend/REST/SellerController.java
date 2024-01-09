package com.shopshow.backend.REST;

import com.shopshow.backend.entities.Seller;
import com.shopshow.backend.services.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/seller")
@Validated
public class SellerController {
    @Autowired
    private SellerService sellerService;
    @GetMapping("/test")
    public String test(){
        return "This is Seller Test For Route!";
    }

    @GetMapping("/myProfile")
    public ResponseEntity<Optional<Seller>> getSellerById(@RequestHeader(value = "Authorization") String authorizationHeader){
        return sellerService.getSellerById(authorizationHeader);
    }

    @PostMapping("/newSeller")
    public ResponseEntity<Seller> newSeller(@Valid @RequestBody Seller seller){
        return sellerService.newSeller(seller);
    }

    @PatchMapping("/updateProfile")
    public ResponseEntity<Seller> updateSellerProfile(@RequestBody Seller updatedSeller,@RequestHeader(value = "Authorization") String authorizationHeader){
        return sellerService.updateSellerProfile(updatedSeller,authorizationHeader);
    }
}
