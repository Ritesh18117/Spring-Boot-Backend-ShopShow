package com.shopshow.backend.REST;
import com.shopshow.backend.entities.Address;
import com.shopshow.backend.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/test")
    public String test(){
        return "This is test from Address Controller!";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Address>> getAllAddress(){
        return addressService.getAllAddress();
    }

    @PostMapping("/addAddress")
    public ResponseEntity<Address> addAddress(@RequestBody Address address){
        return addressService.addAddress(address);
    }
}
