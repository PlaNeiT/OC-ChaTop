package com.openclassrooms.OC_ChaTop.repositories;

import com.openclassrooms.OC_ChaTop.entities.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for the Messages entity, providing CRUD operations and custom queries.
 */
public interface MessagesRepository extends JpaRepository<Messages, Long> {

    /**
     * Finds a list of messages associated with a specific rental by rental ID.
     * @param rentalId the ID of the rental
     * @return a list of messages linked to the specified rental
     */
    List<Messages> findByRentalId(Long rentalId);

    /**
     * Finds a list of messages associated with a specific user by user ID.
     * @param userId the ID of the user
     * @return a list of messages linked to the specified user
     */
    List<Messages> findByUserId(Long userId);
}
