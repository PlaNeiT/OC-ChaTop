package com.openclassrooms.OC_ChaTop.services;

import com.openclassrooms.OC_ChaTop.dtos.MessageRequest;
import com.openclassrooms.OC_ChaTop.dtos.MessageResponse;
import com.openclassrooms.OC_ChaTop.entities.Messages;
import com.openclassrooms.OC_ChaTop.entities.User;
import com.openclassrooms.OC_ChaTop.entities.Rental;
import com.openclassrooms.OC_ChaTop.repositories.MessagesRepository;
import com.openclassrooms.OC_ChaTop.repositories.RentalRepository;
import com.openclassrooms.OC_ChaTop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service class for handling message-related operations, particularly creating messages.
 */
@Service
public class MessagesService {

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalRepository rentalRepository;

    /**
     * Creates a new message associated with a user and a rental.
     *
     * @param messageRequest the request containing message details, user ID, and rental ID
     * @return the created message details as a MessageResponse
     */
    public MessageResponse createMessage(MessageRequest messageRequest) {
        Messages message = new Messages();

        // Fetch the user from the database using the user ID from the request
        Optional<User> userInDB = userRepository.findById(Long.valueOf(messageRequest.getUser_id()));
        if (userInDB.isPresent()) {
            message.setUser(userInDB.get());
        } else {
            throw new RuntimeException("User not found");
        }

        // Fetch the rental from the database using the rental ID from the request
        Optional<Rental> rentalInDB = rentalRepository.findById(messageRequest.getRental_id());
        if (rentalInDB.isPresent()) {
            message.setRental(rentalInDB.get());
        } else {
            throw new RuntimeException("Rental not found");
        }

        // Set message content and timestamps
        message.setMessage(messageRequest.getMessage());
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());

        // Save the message to the database
        messagesRepository.save(message);

        // Return a response object with the saved message details
        return new MessageResponse(
                message.getId(),
                message.getRental().getId(),
                message.getUser().getId(),
                message.getMessage(),
                message.getCreatedAt(),
                message.getUpdatedAt()
        );
    }
}
