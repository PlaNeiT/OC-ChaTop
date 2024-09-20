package com.openclassrooms.OC_ChaTop.controllers;

import com.openclassrooms.OC_ChaTop.dtos.MessageRequest;
import com.openclassrooms.OC_ChaTop.dtos.MessageResponse;
import com.openclassrooms.OC_ChaTop.services.MessagesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing messages related to rentals.
 * Provides an endpoint for creating a new message associated with a specific rental.
 */
@RestController
@RequestMapping("/api")
public class MessagesController {

    @Autowired
    private MessagesService messagesService; // Service to handle message-related operations

    /**
     * Creates a new message related to a specific rental.
     *
     * @param messageRequest the request body containing message details
     * @return MessageResponse containing the created message details
     */
    @Operation(summary = "Create a message", description = "Creates a new message related to a specific rental.",
            security = { @SecurityRequirement(name = "Bearer Authentication") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message created successfully",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping(value = "/messages")
    public MessageResponse createUser(@RequestBody MessageRequest messageRequest) {
        return messagesService.createMessage(messageRequest); // Create and return the new message
    }
}
