package com.shopshow.backend.services;

import com.shopshow.backend.dao.CustomerRepository;
import com.shopshow.backend.dao.ProductRepository;
import com.shopshow.backend.dao.SellerRepository;
import com.shopshow.backend.entities.Customer;
import com.shopshow.backend.entities.Product;
import com.shopshow.backend.entities.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<List<Seller>> getAllSeller(){
        try{
            List<Seller> sellerList = (List<Seller>) sellerRepository.findAll();
            if(sellerList.size() <= 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(sellerList));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Customer>> getAllCustomer(){
        try{
            List<Customer> customerList = (List<Customer>) customerRepository.findAll();
            if(customerList.size() <= 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.of(Optional.of(customerList));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<Product>> getAllProduct(){
        try{
            List<Product> products = (List<Product>) productRepository.findAll();
            if(products.size() <= 0)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.of(Optional.of(products));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
