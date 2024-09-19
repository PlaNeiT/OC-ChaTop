package com.openclassrooms.OC_ChaTop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents the Rental entity in the database, linking rental properties to their owners and messages.
 */
@Entity
@Table(name = "RENTALS")
@EntityListeners(AuditingEntityListener.class) // Enables automatic handling of createdAt and updatedAt fields
@Getter
@Setter
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremented primary key
    private Long id;

    private String name; // Name of the rental property

    @Column(precision = 10, scale = 2) // Surface area of the rental
    private Integer surface;

    @Column(precision = 10, scale = 2) // Price of the rental property
    private Integer price;

    private String picture; // URL or path to the rental's picture

    @Column(length = 2000) // Description of the rental with a maximum length of 2000 characters
    private String description;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetch strategy for the owner to improve performance
    @JoinColumn(name = "owner_id", nullable = false) // Foreign key linking to the User entity
    private User owner;

    @CreatedDate // Automatically captures when the rental was created
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // Automatically captures when the rental was last updated
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    // One-to-many relationship with Messages, cascading all operations and handling orphan removal
    private List<Messages> messages;

}
