package com.openclassrooms.OC_ChaTop.repositories;

import com.openclassrooms.OC_ChaTop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for the User entity, providing CRUD operations and custom queries.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email.
     * @param email the email of the user
     * @return an Optional containing the user if found, or empty if not found
     */
    Optional<User> findByEmail(String email);
}
