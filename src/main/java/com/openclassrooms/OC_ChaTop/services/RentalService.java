package com.openclassrooms.OC_ChaTop.services;

import com.openclassrooms.OC_ChaTop.dtos.RentalRequest;
import com.openclassrooms.OC_ChaTop.dtos.RentalResponse;
import com.openclassrooms.OC_ChaTop.mappers.RentalMapper;
import com.openclassrooms.OC_ChaTop.entities.Rental;
import com.openclassrooms.OC_ChaTop.entities.User;
import com.openclassrooms.OC_ChaTop.repositories.UserRepository;
import com.openclassrooms.OC_ChaTop.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing rental operations.
 */
@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalMapper rentalMapper;

    @Autowired
    private PictureService pictureService;

    /**
     * Creates a new rental with the provided details.
     *
     * @param rentalRequest the details of the rental to be created
     * @return a RentalResponse object containing the created rental details
     */
    public RentalResponse createRental(RentalRequest rentalRequest) {
        // Check if a rental with the same name already exists
        Optional<Rental> rentalInDB = rentalRepository.findByName(rentalRequest.getName());
        if (rentalInDB.isPresent()) {
            throw new RuntimeException("Rental already exists");
        }

        // Initialize a new Rental entity
        Rental rental = new Rental();

        // Get the current authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        Optional<User> userInDB = userRepository.findById(currentUser.getId());
        if (userInDB.isPresent()) {
            rental.setOwner(userInDB.get());
        } else {
            throw new RuntimeException("User not found");
        }

        // Set rental properties
        rental.setName(rentalRequest.getName());
        rental.setSurface(rentalRequest.getSurface());
        rental.setPrice(rentalRequest.getPrice());
        rental.setDescription(rentalRequest.getDescription());
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());

        // Upload picture if provided and set the URL
        if (!rentalRequest.getPicture().isEmpty()) {
            String imageUrl = pictureService.uploadFile(rentalRequest.getPicture());
            rental.setPicture(imageUrl);
        }

        // Save the rental to the database
        rentalRepository.save(rental);

        // Return the response with rental details
        return new RentalResponse(rental.getId(), rental.getName(), rental.getSurface(), rental.getPrice(),
                rental.getPicture(), rental.getDescription(), rental.getOwner().getId(), rental.getCreatedAt(), rental.getUpdatedAt());
    }

    /**
     * Retrieves all rentals from the database.
     *
     * @return a list of RentalResponse objects containing rental details
     */
    public List<RentalResponse> getAllRentals() {
        return rentalRepository.findAll()
                .stream().map(rentalMapper).toList(); // Maps entities to DTOs
    }

    /**
     * Retrieves a rental by its ID.
     *
     * @param id the ID of the rental to be retrieved
     * @return a RentalResponse object containing the rental details
     */
    public RentalResponse getRentalById(Integer id) {
        Optional<Rental> rentalInDB = rentalRepository.findById(id);
        if (rentalInDB.isPresent()) {
            Rental rental = rentalInDB.get();
            return new RentalResponse(rental.getId(), rental.getName(), rental.getSurface(), rental.getPrice(),
                    rental.getPicture(), rental.getDescription(), rental.getOwner().getId(), rental.getCreatedAt(), rental.getUpdatedAt());
        } else {
            throw new RuntimeException("Rental not found");
        }
    }

    /**
     * Updates an existing rental with new details.
     *
     * @param id             the ID of the rental to be updated
     * @param rentalRequest  the updated details of the rental
     * @return a RentalResponse object containing the updated rental details
     */
    public RentalResponse updateRental(Integer id, RentalRequest rentalRequest) throws AccessDeniedException {
        Optional<Rental> rentalInDB = rentalRepository.findById(id);
        if (rentalInDB.isEmpty()) {
            throw new RuntimeException("Rental not found");
        }

        // Check if the authenticated user is the owner of the rental
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        Rental rental = rentalInDB.get();
        if (!rental.getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not authorized to modify this rental");
        }

        // Update rental details using the helper method
        rental = updateRentalDetails(rentalRequest, rentalInDB);
        rentalRepository.save(rental);

        return new RentalResponse(rental.getId(), rental.getName(), rental.getSurface(), rental.getPrice(), rental.getPicture(),
                rental.getDescription(), rental.getOwner().getId(), rental.getCreatedAt(), rental.getUpdatedAt());
    }


    /**
     * Helper method to update the rental entity with the new request details.
     *
     * @param rentalRequest  the request containing new rental details
     * @param rentalInDB     the existing rental entity
     * @return the updated rental entity
     */
    private static Rental updateRentalDetails(RentalRequest rentalRequest, Optional<Rental> rentalInDB) {
        if (rentalInDB.isPresent()) {
            Rental rental = rentalInDB.get();
            if (rentalRequest.getName() != null) {
                rental.setName(rentalRequest.getName());
            }
            if (rentalRequest.getSurface() != null) {
                rental.setSurface(rentalRequest.getSurface());
            }
            if (rentalRequest.getPrice() != null) {
                rental.setPrice(rentalRequest.getPrice());
            }
            if (rentalRequest.getDescription() != null) {
                rental.setDescription(rentalRequest.getDescription());
            }
            return rental;
        } else {
            throw new RuntimeException("Rental not found");
        }
    }
}
