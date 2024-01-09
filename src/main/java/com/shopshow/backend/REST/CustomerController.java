package com.shopshow.backend.REST;

import com.shopshow.backend.entities.Customer;
import com.shopshow.backend.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@Validated
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/test")
    public String test(){
        return "This is testing from Customer Controller";
    }

    @GetMapping("/id")
    public ResponseEntity<Customer> getCustomerById(@RequestHeader(value = "Authorization") String authorizationHeader){
        return customerService.getCustomerById(authorizationHeader);
    }
    @PostMapping("/addCustomer")
    public ResponseEntity<Customer> addCustomer(@Valid @RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }
    @PatchMapping("/updateCustomer")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer updateCustomer,@RequestHeader(value = "Authorization") String authorizationHeader){
        return customerService.updateCustomerProfile(updateCustomer,authorizationHeader);
    }
}
