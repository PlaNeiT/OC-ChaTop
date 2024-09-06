package com.openclassrooms.OC_ChaTop.controllers;

import com.openclassrooms.OC_ChaTop.models.User;
import com.openclassrooms.OC_ChaTop.repositories.UserRepository;
import com.openclassrooms.OC_ChaTop.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    // Route to register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    // Route to login a user and return a JWT token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User dbUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(dbUser.getEmail());
            String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }

    // Route to return the current logged-in user details
    @GetMapping("/me")
    public ResponseEntity<?> getUserDetails(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7); // remove "Bearer " prefix
        String username = jwtService.extractUsername(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return ResponseEntity.ok(userDetails);
    }
}
