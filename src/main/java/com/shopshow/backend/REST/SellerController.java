package com.shopshow.backend.REST;

import com.shopshow.backend.entities.Seller;
import com.shopshow.backend.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
public class SellerController {
    @Autowired
    private SellerService sellerService;
    @GetMapping("/test")
    public String test(){
        return "This is Seller Test For Route!";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Seller>> getAll(){
        return sellerService.getAllSeller();
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable("id") Long id){
        return sellerService.getSellerById(id);
    }

    @PostMapping("/newSeller")
    public ResponseEntity<Seller> newSeller(@RequestBody Seller seller){
        return sellerService.newSeller(seller);
    }
}
