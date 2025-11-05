package com.weldon.supershop.model;

import jakarta.persistence.*;

// Entity annotation marks this class as a database table
@Entity
@Table(name = "products")
public class Product {

    // Primary key with auto-increment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Product name column
    @Column(nullable = false)
    private String name;

    // Product description column
    private String description;

    // Product price column
    @Column(nullable = false)
    private double price;

    // Product image URL (path to product image)
    private String imageUrl;

    // Product category
    private String category;

    // Default constructor (required by JPA)
    public Product() {}

    // Parameterized constructor for easy object creation
    public Product(String name, String description, double price, String imageUrl, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    // Getters and Setters for each field

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }
}
