package com.shopshow.backend.REST;

import com.shopshow.backend.dao.UserRepository;
import com.shopshow.backend.entities.AuthRequest;
import com.shopshow.backend.entities.User;
import com.shopshow.backend.services.JwtService;
import com.shopshow.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/test")
    public String test(){
        return "This is test for User request";
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user){
        return userService.addUser(user);
    }

    @PostMapping("/generateToken")
    public Map<String, String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        System.out.println(authRequest);
        User user = userRepository.findByUsername(authRequest.getUsername());
        if(user != null){
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            System.out.println(authentication.isAuthenticated());
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(authRequest.getUsername());
                Map<String, String> response = new HashMap<>();
                response.put("role",user.getRole());
                response.put("token", token);
                return response;
            } else {
                throw new UsernameNotFoundException("invalid user request !");
            }
        }else {
            throw new UsernameNotFoundException("invalid User!!");
        }
    }
    // It is here for testing
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll(){
        return userService.getAllUser();
    }

}
