package com.openclassrooms.OC_ChaTop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.openclassrooms.OC_ChaTop.models.Rental;

import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    Optional<Rental> findByName(String name);
}
