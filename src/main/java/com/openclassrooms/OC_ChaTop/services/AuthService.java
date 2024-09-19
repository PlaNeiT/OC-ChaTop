package com.openclassrooms.OC_ChaTop.services;

import com.openclassrooms.OC_ChaTop.entities.User;
import com.openclassrooms.OC_ChaTop.dtos.AuthResponse;
import com.openclassrooms.OC_ChaTop.dtos.UserRequest;
import com.openclassrooms.OC_ChaTop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for handling authentication-related operations.
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Authenticates a user based on provided credentials.
     *
     * @param userRequest the request object containing the user's credentials
     * @return an AuthResponse containing the JWT token and expiration time
     */
    public AuthResponse authenticateUser(UserRequest userRequest) {
        // Find user in the database by email
        Optional<User> userInDB = userRepository.findByEmail(userRequest.getEmail());
        if (userInDB.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        // Authenticate the user using the provided credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getEmail(),
                        userRequest.getPassword()
                )
        );

        // Generate a JWT token for the authenticated user
        User user = userInDB.get();
        String jwtToken = jwtService.generateToken(user);

        // Create and return the authentication response
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtToken);
        authResponse.setExpiresIn(jwtService.getExpirationTime());

        return authResponse;
    }
}
