package com.openclassrooms.OC_ChaTop.repositories;

import com.openclassrooms.OC_ChaTop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
