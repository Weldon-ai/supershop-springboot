// File: src/main/java/com/weldon/supershop/repository/RoleRepository.java

package com.weldon.supershop.repository; // Package for data access layer classes

// Import Spring Data JPA and model dependencies
import com.weldon.supershop.model.Role; // Role entity
import org.springframework.data.jpa.repository.JpaRepository; // Provides built-in CRUD operations
import org.springframework.stereotype.Repository; // Marks this interface as a repository bean

@Repository // Makes this class a Spring-managed repository bean
public interface RoleRepository extends JpaRepository<Role, Long> {
    // JpaRepository<Role, Long> means it manages Role entities with primary key type Long

    // Custom method to find a role by name
    Role findByName(String name); // Example: findByName("ADMIN")
}
