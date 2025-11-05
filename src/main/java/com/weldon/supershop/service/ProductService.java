package com.weldon.supershop.service;

import com.weldon.supershop.model.Product;
import com.weldon.supershop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

// Service layer handles business logic between controller and repository
@Service
public class ProductService {

    private final ProductRepository productRepository;

    // Constructor-based dependency injection
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Save or update a product
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // Retrieve all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Retrieve a product by its ID
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // Delete a product by ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // Retrieve products by category
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
}
