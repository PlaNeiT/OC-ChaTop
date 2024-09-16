package com.openclassrooms.OC_ChaTop.services;

import com.openclassrooms.OC_ChaTop.models.User;
import com.openclassrooms.OC_ChaTop.dtos.AuthResponse;
import com.openclassrooms.OC_ChaTop.dtos.UserRequest;

import com.openclassrooms.OC_ChaTop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse authenticateUser(UserRequest userRequest) {
        Optional<User> userInDB = userRepository.findByEmail(userRequest.getEmail());
        if (userInDB.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = userInDB.get();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getEmail(),
                        userRequest.getPassword()
                )
        );

        String jwtToken = jwtService.generateToken((UserDetails) user);

        AuthResponse AuthResponse = new AuthResponse();
        AuthResponse.setToken(jwtToken);
        AuthResponse.setExpiresIn(jwtService.getExpirationTime());

        return AuthResponse;
    }
}