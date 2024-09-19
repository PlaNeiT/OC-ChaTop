package com.openclassrooms.OC_ChaTop.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Represents the User entity which implements UserDetails for Spring Security integration.
 */
@Entity
@Table(name = "USERS")
@EntityListeners(AuditingEntityListener.class) // Enables automatic management of createdAt and updatedAt fields
public class User implements UserDetails {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremented primary key
    private Long id;

    @Setter
    @Getter
    @Column(nullable = false, unique = true) // Ensures the email is unique and not null
    private String email;

    @Setter
    @Getter
    private String name; // User's name

    @Setter
    @Getter
    @Column(nullable = false) // Ensures the password is not null
    private String password;

    @CreatedDate
    @Setter
    @Getter
    @Column(name = "created_at", updatable = false) // Automatically sets the creation timestamp
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Getter
    @Setter
    @Column(name = "updated_at") // Automatically updates the last modified timestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    // One-to-many relationship with rentals, handling cascade operations and orphan removal
    private List<Rental> rentals;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    // One-to-many relationship with messages, handling cascade operations and orphan removal
    private List<Messages> messages;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // No authorities are assigned to users for now
    }

    @Override
    public String getPassword() {
        return this.password; // Returns the user's password
    }

    @Override
    public String getUsername() {
        return this.email; // Uses email as the username for authentication
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Indicates that the account is not expired
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Indicates that the account is not locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Indicates that the credentials are not expired
    }

    @Override
    public boolean isEnabled() {
        return true; // Indicates that the account is enabled
    }
}
