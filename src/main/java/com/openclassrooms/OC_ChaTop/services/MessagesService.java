package com.openclassrooms.OC_ChaTop.services;

import com.openclassrooms.OC_ChaTop.dtos.MessageRequest;

import com.openclassrooms.OC_ChaTop.dtos.MessageResponse;
import com.openclassrooms.OC_ChaTop.models.Messages;
import com.openclassrooms.OC_ChaTop.models.User;
import com.openclassrooms.OC_ChaTop.models.Rental;
import com.openclassrooms.OC_ChaTop.repositories.MessagesRepository;
import com.openclassrooms.OC_ChaTop.repositories.RentalRepository;
import com.openclassrooms.OC_ChaTop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class MessagesService {

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalRepository rentalRepository;


    public MessageResponse createMessage(MessageRequest messageRequest) {
        Messages message = new Messages();

        Optional<User> userInDB = userRepository.findById(Long.valueOf(messageRequest.getUser_id()));
        if (userInDB.isPresent()){
            message.setUser(userInDB.get());
        } else {
            throw new RuntimeException("User not found");
        }
        Optional<Rental> rentalInDB = rentalRepository.findById(messageRequest.getRental_id());
        if (rentalInDB.isPresent()){
            message.setRental(rentalInDB.get());
        } else {
            throw new RuntimeException("Rental not found");
        }

        message.setMessage(messageRequest.getMessage());
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());

        messagesRepository.save(message);
        return new MessageResponse(message.getId(), message.getRental().getId(), message.getUser().getId(), message.getMessage(), message.getCreatedAt(), message.getUpdatedAt());


    }
}
