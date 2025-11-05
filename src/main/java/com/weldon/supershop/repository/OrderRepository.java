// ============================================
// File: OrderRepository.java
// Package: com.weldon.supershop.repository
// Description: Spring Data JPA repository for orders
// ============================================

package com.weldon.supershop.repository; // Make sure package matches

import com.weldon.supershop.model.Order; // Import Order entity
import org.springframework.data.jpa.repository.JpaRepository; // JPA repository base class
import org.springframework.stereotype.Repository; // Marks this as repository

@Repository // Spring recognizes this as a repository bean
public interface OrderRepository extends JpaRepository<Order, Long> {
    // JpaRepository provides:
    // save(), findById(), findAll(), delete(), etc.
}
