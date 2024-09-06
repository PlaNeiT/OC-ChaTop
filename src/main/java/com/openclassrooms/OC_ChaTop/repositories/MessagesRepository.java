package com.openclassrooms.OC_ChaTop.repositories;

import com.openclassrooms.OC_ChaTop.models.Messages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessagesRepository extends JpaRepository<Messages, Long> {
    List<Messages> findByRentalId(Long rentalId);
    List<Messages> findByUserId(Long userId);
}
