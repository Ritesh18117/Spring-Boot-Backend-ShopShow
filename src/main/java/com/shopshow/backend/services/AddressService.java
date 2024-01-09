package com.shopshow.backend.services;

import com.shopshow.backend.dao.AddressRepository;
import com.shopshow.backend.dao.CustomerRepository;
import com.shopshow.backend.dao.UserRepository;
import com.shopshow.backend.entities.Address;
import com.shopshow.backend.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JwtService jwtService;

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

    public ResponseEntity<Address> addAddress(@RequestBody Address address,@RequestHeader(value = "Authorization") String authorizationHeader){
        try{
            String token = extractTokenFromHeader(authorizationHeader);
            String username = jwtService.extractUsername(token);
            Long userId = userRepository.findByUsername(username).getId();
            if( userId != null && Objects.equals(userRepository.findByUsername(username).getRole(), "ROLE_CUSTOMER")){
                Customer customer = customerRepository.findByUserId(userId);
                address.setCustomer(customer);
            }
            addressRepository.save(address);
            return ResponseEntity.of(Optional.of(address));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<String> deleteAddress(long id){
        try{
            addressRepository.deleteById(id);
            return ResponseEntity.ok("Deleted Sucessfully!!");
        } catch (Exception e){
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
