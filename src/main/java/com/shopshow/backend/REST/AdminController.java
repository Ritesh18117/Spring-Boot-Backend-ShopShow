package com.shopshow.backend.REST;

import com.shopshow.backend.entities.Customer;
import com.shopshow.backend.entities.Seller;
import com.shopshow.backend.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @GetMapping("/getAllSeller")
    public ResponseEntity<List<Seller>> getAllSeller(){
        return adminService.getAllSeller();
    }

    @GetMapping("/getAllCustomer")
    public ResponseEntity<List<Customer>> getAllCustomer(){
        return adminService.getAllCustomer();
    }

    @GetMapping("/adminProfile")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }
}
