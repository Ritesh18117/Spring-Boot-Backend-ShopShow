package com.shopshow.backend.services;

import com.shopshow.backend.dao.SellerRepository;
import com.shopshow.backend.entities.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    public ResponseEntity<List<Seller>> getAllSeller(){
        try{
            List<Seller> list = (List<Seller>) sellerRepository.findAll();
            if(list.size() <= 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(list));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Seller> getSellerById(@PathVariable("id") Long id){
        try{
            Optional<Seller> seller = sellerRepository.findById(id);
            return ResponseEntity.of(seller);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

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
