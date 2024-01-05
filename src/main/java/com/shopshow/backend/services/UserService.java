package com.shopshow.backend.services;

import com.shopshow.backend.dao.UserRepository;
import com.shopshow.backend.entities.Customer;
import com.shopshow.backend.entities.Seller;
import com.shopshow.backend.entities.User;
import com.shopshow.backend.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private CustomerService customerService;
    public ResponseEntity<List<User>> getAllUser(){
        try{
            List<User> users = (List<User>) userRepository.findAll();
            if(users.size() <= 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.of(Optional.of(users));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<User> addUser(@RequestBody User user){
        try{
            userRepository.save(user);
            if(user.getRole() == UserRole.ROLE_SELLER){
                Seller seller = new Seller();
                seller.setUser(user);
                sellerService.newSeller(seller);
            }
            if(user.getRole() == UserRole.ROLE_CUSTOMER){
                Customer customer = new Customer();
                customer.setUser(user);
                customerService.addCustomer(customer);
            }
            return ResponseEntity.of(Optional.of(user));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
