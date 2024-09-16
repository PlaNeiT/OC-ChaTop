package com.openclassrooms.OC_ChaTop.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private Long id;
    private Long rental;
    private Long user;
    private String message;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;


}