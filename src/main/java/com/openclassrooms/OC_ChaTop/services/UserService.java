package com.openclassrooms.OC_ChaTop.services;

import com.openclassrooms.OC_ChaTop.dtos.UserRequest;
import com.openclassrooms.OC_ChaTop.dtos.UserResponse;
import com.openclassrooms.OC_ChaTop.models.User;
import com.openclassrooms.OC_ChaTop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<UserResponse> getUserById(Long id) {
        Optional<User> userInDB = userRepository.findById(id);
        if (userInDB.isEmpty()){
            throw new RuntimeException("User not found");
        }
        User user = userInDB.get();
        return Optional.of(new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt()));
    }

    public UserResponse getUserByEmail(String email) {
        Optional<User> userInDB = userRepository.findByEmail(email);
        if (userInDB.isEmpty()){
            throw new RuntimeException("User not found");
        }
        User user = userInDB.get();
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }

    public void createUser(UserRequest userRequest) {
        Optional<User> userInDB = userRepository.findByEmail(userRequest.getEmail());
        if (userInDB.isPresent()){
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }

}
