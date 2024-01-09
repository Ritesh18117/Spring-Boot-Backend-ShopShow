package com.shopshow.backend.REST;

import com.shopshow.backend.entities.Product;
import com.shopshow.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@Validated
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/test")
    public String test(){
        return "This is test request from Product controller";
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product,@RequestHeader(value = "Authorization") String authorizationHeader){
        return productService.addProduct(product,authorizationHeader);
    }
}


