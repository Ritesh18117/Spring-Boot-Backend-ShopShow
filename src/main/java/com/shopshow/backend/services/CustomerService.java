package com.shopshow.backend.services;

import com.shopshow.backend.dao.CustomerRepository;
import com.shopshow.backend.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public ResponseEntity<List<Customer>> getAllCustomer(){
        try{
            List<Customer> customers = (List<Customer>) customerRepository.findAll();
            if(customers.size() <= 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.of(Optional.of(customers));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        try{
            customerRepository.save(customer);
            return ResponseEntity.of(Optional.of(customer));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
