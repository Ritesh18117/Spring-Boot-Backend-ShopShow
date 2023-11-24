package com.shopshow.backend.services;

import com.shopshow.backend.dao.ProductVariationRepository;
import com.shopshow.backend.entities.ProductVariation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class ProductVariationServices {
    @Autowired
    private ProductVariationRepository productVariationRepository;

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
