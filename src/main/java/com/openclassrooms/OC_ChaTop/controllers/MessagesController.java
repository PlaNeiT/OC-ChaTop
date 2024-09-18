package com.openclassrooms.OC_ChaTop.controllers;

import com.openclassrooms.OC_ChaTop.dtos.MessageRequest;
import com.openclassrooms.OC_ChaTop.dtos.MessageResponse;
import com.openclassrooms.OC_ChaTop.services.MessagesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @Operation(summary = "Create a message", description = "Creates a new message related to a specific rental.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message created successfully", content = @Content(schema = @Schema(implementation = MessageResponse.class)))
    })
    @PostMapping(value = "/messages")
    public MessageResponse createUser(@RequestBody MessageRequest messageRequest){
        return messagesService.createMessage(messageRequest);
    }
}
