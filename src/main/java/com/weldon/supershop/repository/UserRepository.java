// File: src/main/java/com/weldon/supershop/repository/UserRepository.java

package com.weldon.supershop.repository; // Declares this file belongs to the repository layer

// Import dependencies
import java.util.Optional;
import com.weldon.supershop.model.User; // User entity
import org.springframework.data.jpa.repository.JpaRepository; // Provides CRUD operations automatically
import org.springframework.stereotype.Repository; // Marks this as a Spring component

@Repository // Marks this as a repository so Spring can auto-detect it
public interface UserRepository extends JpaRepository<User, Long> {
    // This interface handles database operations for User entities (Long = primary key type)

    // ==========================
    // Custom Finder Methods
    // ==========================
Optional<User> findByUsername(String username); // ✅ Add this method

    // Finds a user by their email (used during login or signup validation)
    User findByEmail(String email);

    // Checks if a user already exists by email (useful for preventing duplicate registrations)
    boolean existsByEmail(String email);
}
