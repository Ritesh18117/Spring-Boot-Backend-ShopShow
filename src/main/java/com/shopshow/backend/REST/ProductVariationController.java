package com.shopshow.backend.REST;

import com.shopshow.backend.entities.ProductVariation;
import com.shopshow.backend.services.ProductVariationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productVariation")
public class ProductVariationController {
    @Autowired
    private ProductVariationServices productVariationServices;

    @GetMapping("/test")
    public String test(){
        return "This is test for ProductVariation request";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductVariation>> getAllProductVariation(){
        return productVariationServices.getAllProductVariation();
    }
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PostMapping("/addProductVariation")
    public ResponseEntity<ProductVariation> addProductVariation(@RequestBody ProductVariation productVariation){
        return productVariationServices.addProductVariation(productVariation);
    }

}
