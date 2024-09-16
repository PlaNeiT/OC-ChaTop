package com.openclassrooms.OC_ChaTop.services;

import com.openclassrooms.OC_ChaTop.dtos.RentalRequest;
import com.openclassrooms.OC_ChaTop.dtos.RentalResponse;
import com.openclassrooms.OC_ChaTop.mappers.RentalMapper;
import com.openclassrooms.OC_ChaTop.models.Rental;
import com.openclassrooms.OC_ChaTop.models.User;
import com.openclassrooms.OC_ChaTop.repositories.UserRepository;
import com.openclassrooms.OC_ChaTop.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalMapper rentalMapper;

    public RentalResponse createRental(RentalRequest rentalRequest) {
        Optional<Rental> rentalInDB = rentalRepository.findByName(rentalRequest.getName());
        if (rentalInDB.isPresent()){
            throw new RuntimeException("Rental already exists");
        }

        Rental rental = new Rental();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        Optional<User> userInDB = userRepository.findById(currentUser.getId());
        if (userInDB.isPresent()){
            rental.setOwner(userInDB.get());
        } else {
            throw new RuntimeException("User not found");
        }

        rental.setName(rentalRequest.getName());
        rental.setSurface(rentalRequest.getSurface());
        rental.setPrice(rentalRequest.getPrice());
        rental.setDescription(rentalRequest.getDescription());
        rental.setCreatedAt(LocalDateTime.now());
        rental.setUpdatedAt(LocalDateTime.now());

        rentalRepository.save(rental);

        return new RentalResponse(rental.getId(), rental.getName(), rental.getSurface(), rental.getPrice(), rental.getPicture(), rental.getDescription(), rental.getOwner().getId(), rental.getCreatedAt(), rental.getUpdatedAt());
    }

    public List<RentalResponse> getAllRentals() {
        return rentalRepository.findAll()
                .stream().map(rentalMapper).toList();
    }

    public RentalResponse getRentalById(Integer id) {
        Optional<Rental> rentalInDB = rentalRepository.findById(id);
        if(rentalInDB.isPresent()) {
            Rental rental = rentalInDB.get();
            return new RentalResponse(rental.getId(), rental.getName(), rental.getSurface(), rental.getPrice(), rental.getPicture(), rental.getDescription(), rental.getOwner().getId(), rental.getCreatedAt(), rental.getUpdatedAt());
        } else {
            throw new RuntimeException("Rental not found");
        }
    }

    public RentalResponse updateRental(Integer id, RentalRequest rentalRequest){
        Optional<Rental> rentalInDB = rentalRepository.findById(id);
        if(rentalInDB.isEmpty()){
            throw new RuntimeException("Rental not found");
        }


        Rental rental = getRental(rentalRequest, rentalInDB);
        rentalRepository.save(rental);

        return new RentalResponse(rental.getId(), rental.getName(), rental.getSurface(), rental.getPrice(), rental.getPicture(), rental.getDescription(), rental.getOwner().getId(), rental.getCreatedAt(), rental.getUpdatedAt());
    }

    private static Rental getRental(RentalRequest rentalRequest, Optional<Rental> rentalInDB) {
        if (rentalInDB.isPresent()) {            Rental rental = rentalInDB.get();
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

