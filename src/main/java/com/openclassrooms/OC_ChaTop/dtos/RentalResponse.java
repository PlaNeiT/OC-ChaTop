package com.openclassrooms.OC_ChaTop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * DTO for representing a rental in responses.
 * Contains fields that detail the rental's information.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalResponse {

    private Long id; // Unique identifier of the rental.
    private String name; // Name of the rental property.
    private Integer surface; // Surface area of the rental in square meters.
    private Integer price; // Price of the rental.
    private String picture; // URL or path of the rental's picture.
    private String description; // Description of the rental.
    private Long owner_id; // Identifier of the rental's owner.
    private LocalDateTime created_at; // Timestamp of when the rental was created.
    private LocalDateTime updated_at; // Timestamp of the last update to the rental.
}
