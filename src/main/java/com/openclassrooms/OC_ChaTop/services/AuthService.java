package com.openclassrooms.OC_ChaTop.services;

import com.openclassrooms.OC_ChaTop.models.User;
import com.openclassrooms.OC_ChaTop.dtos.AuthResponse;
import com.openclassrooms.OC_ChaTop.dtos.UserRequest;

import com.openclassrooms.OC_ChaTop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        if (userInDB.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getEmail(),
                        userRequest.getPassword()
                )
        );

        User user = userInDB.get();
        String jwtToken = jwtService.generateToken(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtToken);
        authResponse.setExpiresIn(jwtService.getExpirationTime());

        return authResponse;
    }
}
