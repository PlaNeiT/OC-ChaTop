package com.openclassrooms.OC_ChaTop.mappers;

import com.openclassrooms.OC_ChaTop.dtos.RentalResponse;
import com.openclassrooms.OC_ChaTop.entities.Rental;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Component that maps Rental entity objects to RentalResponse DTOs.
 */
@Component
public class RentalMapper implements Function<Rental, RentalResponse> {

    /**
     * Applies the mapping function to convert a Rental object to a RentalResponse.
     *
     * @param rental the Rental entity to be converted.
     * @return the corresponding RentalResponse DTO.
     */
    @Override
    public RentalResponse apply(Rental rental) {
        // Map the properties of the Rental entity to the RentalResponse DTO
        return new RentalResponse(
                rental.getId(),                      // Rental ID
                rental.getName(),                    // Name of the rental
                rental.getSurface(),                 // Surface area of the rental
                rental.getPrice(),                   // Price of the rental
                rental.getPicture(),                 // Picture URL or binary data
                rental.getDescription(),             // Description of the rental
                rental.getOwner().getId(),           // Owner's ID
                rental.getCreatedAt(),               // Timestamp of creation
                rental.getUpdatedAt()                // Timestamp of the last update
        );
    }
}
