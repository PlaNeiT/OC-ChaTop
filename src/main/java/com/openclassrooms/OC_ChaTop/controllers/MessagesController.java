package com.openclassrooms.OC_ChaTop.controllers;

import com.openclassrooms.OC_ChaTop.dtos.MessageRequest;
import com.openclassrooms.OC_ChaTop.dtos.MessageResponse;
import com.openclassrooms.OC_ChaTop.services.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/messages")
    public MessageResponse createUser(@RequestBody MessageRequest messageRequest){
        return messagesService.createMessage(messageRequest);
    }
}
