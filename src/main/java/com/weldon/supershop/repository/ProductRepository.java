package com.weldon.supershop.repository;

import com.weldon.supershop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repository layer for Product entity
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Custom query to find products by category
    List<Product> findByCategory(String category);
}
