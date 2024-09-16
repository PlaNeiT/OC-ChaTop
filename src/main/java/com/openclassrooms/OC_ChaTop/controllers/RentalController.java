package com.openclassrooms.OC_ChaTop.controllers;


import com.openclassrooms.OC_ChaTop.dtos.RentalRequest;
import com.openclassrooms.OC_ChaTop.dtos.RentalResponse;
import com.openclassrooms.OC_ChaTop.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // todo: add image and all. Get works but not create
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RentalResponse createRental( @ModelAttribute RentalRequest rentalRequest) {
        return rentalService.createRental(rentalRequest);
    }

    @GetMapping("/rentals")
    public Map<String, List<RentalResponse>> getRentals() {
        List<RentalResponse> rentalList = rentalService.getAllRentals();
        Map<String, List<RentalResponse>> response = new HashMap<>();
        response.put("rentals", rentalList);
        return response;
    }

    @GetMapping("/rentals/{id}")
    public RentalResponse getRental(@PathVariable int id) {
        return rentalService.getRentalById(id);
    }

    @PutMapping(value = "/rentals/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RentalResponse updateRental(@PathVariable int id, @ModelAttribute RentalRequest rentalRequest){
        return rentalService.updateRental(id, rentalRequest);
    }

}