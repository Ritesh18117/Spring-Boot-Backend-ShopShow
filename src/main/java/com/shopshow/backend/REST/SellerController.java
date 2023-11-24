package com.shopshow.backend.REST;

import com.shopshow.backend.dao.SellerRepository;
import com.shopshow.backend.entities.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seller")
public class SellerController {
    @Autowired
    private SellerRepository sellerRepository;
    @GetMapping("/test")
    public String test(){
        return "This is Seller Test For Route!";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Seller>> getAll(){
        List<Seller> list = (List<Seller>) sellerRepository.findAll();
        if(list.size() <= 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(list));
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable("id") Long id){
        try{
            Optional<Seller> seller = sellerRepository.findById(id);
            return ResponseEntity.of(seller);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/newSeller")
    public ResponseEntity<Seller> newSeller(@RequestBody Seller seller){
        try{
            sellerRepository.save(seller);
            return ResponseEntity.of(Optional.of(seller));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
