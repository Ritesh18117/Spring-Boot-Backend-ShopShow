package com.shopshow.backend.REST;

import com.shopshow.backend.entities.User;
import com.shopshow.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test(){
        return "This is test for User request";
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll(){
        return userService.getAllUser();
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return userService.addUser(user);
    }
}
