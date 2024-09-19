package com.openclassrooms.OC_ChaTop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO representing the response after user authentication.
 * Contains a JWT token and the expiration time in seconds.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token; // The JWT token issued upon successful authentication
    private long expiresIn; // The time in seconds until the token expires
}
