package com.shopshow.backend.services;

import com.shopshow.backend.dao.CustomerRepository;
import com.shopshow.backend.dao.UserRepository;
import com.shopshow.backend.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@Component
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

//    public ResponseEntity<List<Customer>> getAllCustomer(){
//        try{
//            List<Customer> customers = (List<Customer>) customerRepository.findAll();
//            if(customers.size() <= 0)
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//            return ResponseEntity.of(Optional.of(customers));
//        } catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        try{
            customerRepository.save(customer);
            return ResponseEntity.of(Optional.of(customer));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Optional<Customer>> getCustomerById(@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).get().getId();
            Optional<Customer> customer = customerRepository.findByUserId(userId);
            return ResponseEntity.of(Optional.of(customer));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<Customer> updateCustomerProfile(@RequestBody Customer updatedCustomer, @RequestHeader(value = "Authorization") String authorizationHeader){
        try {
            Optional<Customer> optionalCustomer = getCustomerById(authorizationHeader).getBody();
            if (optionalCustomer.isPresent()) {
                Customer existingSeller = optionalCustomer.get();
                // Copy updated fields from updatedSeller to existingSeller
                BeanUtils.copyProperties(updatedCustomer, existingSeller, "id");
                Customer savedSeller = customerRepository.save(existingSeller);
                return ResponseEntity.of(Optional.of(savedSeller));
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        // Check if the Authorization header is not null and starts with "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token part by removing "Bearer " prefix
            return authorizationHeader.substring(7); // "Bearer ".length() == 7
        }
        return null; // Return null or handle accordingly if token extraction fails
    }
}
