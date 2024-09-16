package com.openclassrooms.OC_ChaTop.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalRequest {
    @Size(max = 256, message = "Le nom ne doit pas dépasser 256 caractères.")
    private String name;

    @Min(value = 9, message = "La superficie du bien doit être au minimum de 9m².")
    private Integer surface;

    @Min(value = 1, message = "Le prix doit au moins être d'un euro.")
    private Integer price;

    private MultipartFile picture;

    @Size(max = 1000, message = "la description ne doit pas dépasser 1000 caractères.")
    private String description;
}