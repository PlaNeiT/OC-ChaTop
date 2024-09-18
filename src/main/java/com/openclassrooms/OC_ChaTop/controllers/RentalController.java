package com.openclassrooms.OC_ChaTop.controllers;


import com.openclassrooms.OC_ChaTop.dtos.RentalRequest;
import com.openclassrooms.OC_ChaTop.dtos.RentalResponse;
import com.openclassrooms.OC_ChaTop.services.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Operation(summary = "Create a new rental", description = "Creates a new rental with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rental created successfully", content = @Content(schema = @Schema(implementation = RentalResponse.class)))
    })    @PostMapping(value = "/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RentalResponse createRental( @ModelAttribute RentalRequest rentalRequest) {
        return rentalService.createRental(rentalRequest);
    }

    @Operation(summary = "Get all rentals", description = "Retrieves all the rentals available.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rentals retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RentalResponse.class))))
    })
    @GetMapping("/rentals")
    public Map<String, List<RentalResponse>> getRentals() {
        List<RentalResponse> rentalList = rentalService.getAllRentals();
        Map<String, List<RentalResponse>> response = new HashMap<>();
        response.put("rentals", rentalList);
        return response;
    }

    @Operation(summary = "Get rental by ID", description = "Fetches rental information for a specific rental by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental found", content = @Content(schema = @Schema(implementation = RentalResponse.class))),
            @ApiResponse(responseCode = "404", description = "Rental not found")
    })
    @GetMapping("/rentals/{id}")
    public RentalResponse getRental(@PathVariable int id) {
        return rentalService.getRentalById(id);
    }

    @Operation(summary = "Update rental by ID", description = "Updates the information of a specific rental by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental updated successfully", content = @Content(schema = @Schema(implementation = RentalResponse.class))),
            @ApiResponse(responseCode = "404", description = "Rental not found")
    })
    @PutMapping(value = "/rentals/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RentalResponse updateRental(@PathVariable int id, @ModelAttribute RentalRequest rentalRequest){
        return rentalService.updateRental(id, rentalRequest);
    }

}