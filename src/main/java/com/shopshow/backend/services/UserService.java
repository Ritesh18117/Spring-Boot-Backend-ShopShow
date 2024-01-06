package com.shopshow.backend.services;

import com.shopshow.backend.dao.UserRepository;
import com.shopshow.backend.entities.Customer;
import com.shopshow.backend.entities.Seller;
import com.shopshow.backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    @Lazy
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userDetail = userRepository.findByUsername(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserDetailss::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
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
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            if(Objects.equals(user.getRole(), "ROLE_SELLER")){
                Seller seller = new Seller();
                seller.setUser(user);
                sellerService.newSeller(seller);
            }
            if(Objects.equals(user.getRole(), "ROLE_CUSTOMER")){
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
