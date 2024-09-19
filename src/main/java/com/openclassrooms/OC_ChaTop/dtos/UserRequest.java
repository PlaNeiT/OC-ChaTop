package com.openclassrooms.OC_ChaTop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * DTO for handling user input when registering or logging in.
 * Includes validation constraints to ensure data integrity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @Size(max = 64, message = "Name must not exceed 64 characters.") // Validates that the name does not exceed 64 characters.
    private String name;

    @Email(message = "Email must be valid.") // Validates that the email is in a valid format.
    private String email;

    @Size(min = 4, max = 64, message = "Password must be between 4 and 64 characters.") // Validates that the password is between 4 and 64 characters.
    private String password;

}
