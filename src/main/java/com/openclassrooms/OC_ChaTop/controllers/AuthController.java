package com.openclassrooms.OC_ChaTop.controllers;

import com.openclassrooms.OC_ChaTop.models.User;
import com.openclassrooms.OC_ChaTop.services.AuthService;
import com.openclassrooms.OC_ChaTop.services.UserService;
import com.openclassrooms.OC_ChaTop.dtos.UserRequest;
import com.openclassrooms.OC_ChaTop.dtos.UserResponse;
import com.openclassrooms.OC_ChaTop.dtos.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public AuthResponse register(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return authService.authenticateUser(userRequest);
    }

    @PostMapping("/auth/login")
    public AuthResponse authenticate(@RequestBody UserRequest userRequest) {
        return authService.authenticateUser(userRequest);
    }

    @GetMapping("/auth/me")
    public UserResponse authenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        try {
            User user = (User) authentication.getPrincipal();
            return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
        } catch (Exception e) {
            throw new RuntimeException("User not found");
        }
    }
}
