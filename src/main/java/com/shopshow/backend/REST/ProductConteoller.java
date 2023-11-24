package com.shopshow.backend.REST;

import com.shopshow.backend.entities.Product;
import com.shopshow.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductConteoller {
    @Autowired
    private ProductService productService;

    @GetMapping("/test")
    public String test(){
        return "This is test request from Product controller";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAllProduct(){
        return productService.getAllProduct();
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }
}


