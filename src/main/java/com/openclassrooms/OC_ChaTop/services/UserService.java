package com.openclassrooms.OC_ChaTop.services;

import com.openclassrooms.OC_ChaTop.dtos.UserRequest;
import com.openclassrooms.OC_ChaTop.dtos.UserResponse;
import com.openclassrooms.OC_ChaTop.entities.User;
import com.openclassrooms.OC_ChaTop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service class responsible for user operations such as retrieval and creation.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to be retrieved
     * @return an Optional containing the UserResponse if found
     */
    public Optional<UserResponse> getUserById(Long id) {
        Optional<User> userInDB = userRepository.findById(id);
        if (userInDB.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userInDB.get();
        return Optional.of(new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt()));
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user to be retrieved
     * @return a UserResponse containing the user's details if found
     */
    public UserResponse getUserByEmail(String email) {
        Optional<User> userInDB = userRepository.findByEmail(email);
        if (userInDB.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userInDB.get();
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
    }

    /**
     * Creates a new user based on the provided UserRequest.
     *
     * @param userRequest the request object containing user details
     */
    public void createUser(UserRequest userRequest) {
        // Check if a user with the same email already exists
        Optional<User> userInDB = userRepository.findByEmail(userRequest.getEmail());
        if (userInDB.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        // Initialize a new User entity
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword())); // Encrypt the password
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Save the new user to the database
        userRepository.save(user);
    }
}
