package com.openclassrooms.OC_ChaTop.controllers;

import com.openclassrooms.OC_ChaTop.dtos.UserResponse;
import com.openclassrooms.OC_ChaTop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    // Route pour obtenir un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<UserResponse> user = userService.getUserById(id);
        return user.<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());


    }
}
