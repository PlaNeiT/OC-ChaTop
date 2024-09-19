package com.openclassrooms.OC_ChaTop.controllers;

import com.openclassrooms.OC_ChaTop.entities.User;
import com.openclassrooms.OC_ChaTop.services.AuthService;
import com.openclassrooms.OC_ChaTop.services.UserService;
import com.openclassrooms.OC_ChaTop.dtos.UserRequest;
import com.openclassrooms.OC_ChaTop.dtos.UserResponse;
import com.openclassrooms.OC_ChaTop.dtos.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Controller that handles user authentication and registration.
 * It provides endpoints for user registration, login, and fetching the authenticated user's details.
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService; // Service to handle user-related operations

    @Autowired
    private AuthService authService; // Service to handle authentication-related operations

    /**
     * Registers a new user with the provided details.
     *
     * @param userRequest the request containing user details for registration
     * @return AuthResponse containing authentication details after successful registration
     */
    @Operation(summary = "Register a new user", description = "Registers a new user with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    })
    @PostMapping("/auth/register")
    public AuthResponse register(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest); // Create new user
        return authService.authenticateUser(userRequest); // Authenticate and return authentication response
    }

    /**
     * Authenticates a user with the provided credentials.
     *
     * @param userRequest the request containing user credentials
     * @return AuthResponse containing authentication details after successful login
     */
    @Operation(summary = "User login", description = "Authenticates a user with the provided credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/auth/login")
    public AuthResponse authenticate(@RequestBody UserRequest userRequest) {
        return authService.authenticateUser(userRequest); // Authenticate and return authentication response
    }

    /**
     * Retrieves information about the currently authenticated user.
     *
     * @return UserResponse containing the authenticated user's information
     */
    @Operation(summary = "Get authenticated user info", description = "Retrieves information about the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User info retrieved successfully",
                    content = @Content(schema = @Schema(implementation = UserResponse.class)))
    })
    @GetMapping("/auth/me")
    public UserResponse authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if user is authenticated and retrieve user details
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            throw new RuntimeException("User not found"); // Throw error if user is not found
        }

        // Return the authenticated user's information
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
