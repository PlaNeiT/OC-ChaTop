package com.openclassrooms.OC_ChaTop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO for returning message details in response.
 * Contains information about the message, including its associated rental and user.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    private Long id; // Unique identifier of the message
    private Long rental; // ID of the associated rental
    private Long user; // ID of the user who created the message
    private String message; // Content of the message
    private LocalDateTime created_at; // Timestamp when the message was created
    private LocalDateTime updated_at; // Timestamp when the message was last updated

}
