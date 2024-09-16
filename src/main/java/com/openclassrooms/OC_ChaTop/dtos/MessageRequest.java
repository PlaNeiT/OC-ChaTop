package com.openclassrooms.OC_ChaTop.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequest {
    @NotNull(message = "La location doit exister.")
    private Integer rental_id;

    @NotNull(message = "Le propriétaire doit exister.")
    private Integer user_id;

    @Size(max = 1000)
    private String message;
}