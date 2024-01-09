package com.shopshow.backend.REST;

import com.shopshow.backend.entities.Product;
import com.shopshow.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product,@RequestHeader(value = "Authorization") String authorizationHeader){
        return productService.addProduct(product,authorizationHeader);
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @GetMapping("/myProducts")
    public ResponseEntity<List<Product>> myProducts(@RequestHeader(value = "Authorization") String authorizationHeader){
        return productService.myProducts(authorizationHeader);
    }
}


