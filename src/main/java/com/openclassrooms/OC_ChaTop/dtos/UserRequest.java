package com.openclassrooms.OC_ChaTop.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @Size(max = 64, message = "Le nom ne doit pas dépasser 64 caractères.")
    String name;

    @Email(message = "L'adresse mail doit être valide.")
    String email;

    @Size(max = 64, message = "Le mot ne passe ne doit pas dépasser 64 caractères.")
    String password;


}