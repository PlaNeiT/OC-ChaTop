package com.openclassrooms.OC_ChaTop.mappers;

import com.openclassrooms.OC_ChaTop.dtos.RentalResponse;
import com.openclassrooms.OC_ChaTop.models.Rental;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RentalMapper implements Function<Rental, RentalResponse> {

    public RentalResponse apply(Rental rental) {
        return new RentalResponse(rental.getId(), rental.getName(), rental.getSurface(), rental.getPrice(), rental.getPicture(), rental.getDescription(), rental.getOwner().getId(), rental.getCreatedAt(), rental.getUpdatedAt());
    }
}
