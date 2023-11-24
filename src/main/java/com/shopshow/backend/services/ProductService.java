package com.shopshow.backend.services;

import com.shopshow.backend.dao.ProductRepository;
import com.shopshow.backend.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

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

    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        try{
            productRepository.save(product);
            return ResponseEntity.of(Optional.of(product));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
