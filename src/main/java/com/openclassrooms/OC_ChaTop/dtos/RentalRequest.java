package com.openclassrooms.OC_ChaTop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

/**
 * DTO for creating or updating a rental.
 * Contains fields for rental details along with validation constraints.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalRequest {

    @Size(max = 256, message = "Name must not exceed 256 characters.")
    private String name; // Name of the rental, with a maximum length of 256 characters.

    @Min(value = 9, message = "Surface area must be at least 9 square meters.")
    private Integer surface; // Surface area of the rental, must be at least 9 square meters.

    @Min(value = 1, message = "Price must be at least 1 euro.")
    private Integer price; // Price of the rental, must be at least 1 euro.

    @Size(max = 512, message = "Address must not exceed 512 characters.")
    private MultipartFile picture; // Image file for the rental, stored as a multipart file.

    @Size(max = 2000, message = "Description must not exceed 2000 characters.")
    private String description; // Description of the rental, with a maximum length of 2000 characters.

}
