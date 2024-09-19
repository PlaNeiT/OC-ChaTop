package com.openclassrooms.OC_ChaTop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Represents the Messages entity in the database, linking messages to specific rentals and users.
 */
@Entity
@Table(name = "MESSAGES")
@EntityListeners(AuditingEntityListener.class) // Enables automatic auditing (createdAt, updatedAt)
@Getter
@Setter
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremented primary key
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetch strategy to optimize performance
    @JoinColumn(name = "rental_id", nullable = false) // Foreign key to the Rental entity
    private Rental rental;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetch strategy to optimize performance
    @JoinColumn(name = "user_id", nullable = false) // Foreign key to the User entity
    private User user;

    @Column(length = 2000) // Specifies the maximum length of the message content
    private String message;

    @CreatedDate // Automatically captures the creation timestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // Automatically updates the timestamp on modification
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
