package com.shopshow.backend.REST;

import com.shopshow.backend.dao.ProductVariationRepository;
import com.shopshow.backend.entities.ProductVariation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productVariation")
public class ProductVariationController {
    @Autowired
    private ProductVariationRepository productVariationRepository;

    @GetMapping("/test")
    public String test(){
        return "This is test for ProductVariation request";
    }

    @GetMapping("/getAll")
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

    @PostMapping("/addProductVariation")
    public ResponseEntity<ProductVariation> addProductVariation(@RequestBody ProductVariation productVariation){
        try{
            productVariationRepository.save(productVariation);
            return ResponseEntity.of(Optional.of(productVariation));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
