package com.shopshow.backend.services;

import com.shopshow.backend.dao.AddressRepository;
import com.shopshow.backend.entities.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    public ResponseEntity<List<Address>> getAllAddress(){
        try{
            List<Address> addresses = (List<Address>) addressRepository.findAll();
            if(addresses.size() <= 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(addresses));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Address> addAddress(@RequestBody Address address){
        try{
            addressRepository.save(address);
            return ResponseEntity.of(Optional.of(address));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
