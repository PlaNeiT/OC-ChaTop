package com.openclassrooms.OC_ChaTop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.openclassrooms.OC_ChaTop.entities.Rental;

import java.util.Optional;

/**
 * Repository interface for the Rental entity, providing CRUD operations and custom queries.
 */
@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {

    /**
     * Finds a rental by its name.
     * @param name the name of the rental
     * @return an Optional containing the rental if found, or empty if not found
     */
    Optional<Rental> findByName(String name);
}
