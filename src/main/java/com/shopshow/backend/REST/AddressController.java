package com.shopshow.backend.REST;

import com.shopshow.backend.entities.Address;
import com.shopshow.backend.entities.Product;
import com.shopshow.backend.services.AddressService;
import com.shopshow.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private ProductService productService;

    @GetMapping("/test")
    public String test(){
        return "This is test from Address Controller!";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Address>> getAllAddress(){
        return addressService.getAllAddress();
    }

    @GetMapping("/myAddresses")
    public ResponseEntity<List<Address>> getMyAddresses(@RequestHeader(value = "Authorization") String authorizationHeader){
        return addressService.myAddresses(authorizationHeader);
    }
    @PostMapping("/addAddress")
    public ResponseEntity<Address> addAddress(@RequestBody Address address,@RequestHeader(value = "Authorization") String authorizationHeader){
        return addressService.addAddress(address,authorizationHeader);
    }
    @DeleteMapping("/deleteAddress/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable("id") long id){
        return addressService.deleteAddress(id);
    }
    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProduct(){
        return productService.getAllProduct();
    }
}
