package com.weldon.supershop.model;

import jakarta.persistence.*;

// Marks this as a JPA entity for the 'order_items' table
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique ID for each order item

    @ManyToOne // Many items belong to one order
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne // Each order item refers to one product
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private int quantity; // Quantity of this product in the order

    @Column(nullable = false)
    private double price; // Price of this item

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}
