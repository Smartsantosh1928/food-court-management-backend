package com.santosh.FCM.controller;

import com.santosh.FCM.model.Response;
import com.santosh.FCM.model.User;
import com.santosh.FCM.repo.UserRepo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepo userRepo;
    @GetMapping("/")
    public void redirect(HttpServletResponse res) throws IOException {
        res.sendRedirect("/swagger-ui/index.html");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        // Check if the user with the given email already exists
        Optional<User> existingUser = userRepo.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            // If the user already exists, return an error response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User with this email already exists");
        } else {
            // If the user does not exist, proceed with registration
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            User savedUser = userRepo.save(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User registered successfully");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Response<User>> login(@RequestBody User userLoginRequest) {
        Optional<User> userOptional = userRepo.findByEmail(userLoginRequest.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (bCryptPasswordEncoder.matches(userLoginRequest.getPassword(), user.getPassword()))
                return ResponseEntity.ok(new Response<User>(true,user,"Login successful"));
            else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<User>(false,null,"Invalid Credentials!"));
        }
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response<User>(false,null,"User not Found!"));
    }

}
