package com.weldon.supershop.repository; // Package for repositories

import com.weldon.supershop.model.Order; // Import the Order entity
import org.springframework.data.jpa.repository.JpaRepository; // JPA repository interface
import org.springframework.stereotype.Repository; // Marks this interface as a repository

@Repository // Tells Spring Boot this is a repository bean
public interface OrderRepository extends JpaRepository<Order, Long> {
    // JpaRepository provides CRUD operations automatically:
    // save(), findById(), findAll(), deleteById(), etc.
}
