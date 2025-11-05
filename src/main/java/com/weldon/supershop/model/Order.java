// Order.java — Represents a customer's order with a unique order number

package com.weldon.supershop.model; // Model package

import jakarta.persistence.*; // Import JPA annotations
import java.time.LocalDateTime; // For saving order time
import java.util.List; // For list of order items
import java.util.UUID; // For generating unique order numbers

@Entity // Marks this class as a database entity
@Table(name = "orders") // Table name in MySQL
public class Order {

    @Id // Marks primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increments ID
    private Long id; // Unique ID for each order

    private String orderNumber; // Unique order reference (like Amazon)
    private String customerName; // Customer’s name
    private String customerEmail; // Customer’s email
    private double totalAmount; // Total paid
    private LocalDateTime orderTime; // Time when order placed

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // Relation to order items
    private List<OrderItem> items; // List of purchased items

    // Default constructor
    public Order() {
        this.orderNumber = generateOrderNumber(); // Auto-generate on creation
    }

    // Constructor with parameters
    public Order(String customerName, String customerEmail, double totalAmount, LocalDateTime orderTime) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.totalAmount = totalAmount;
        this.orderTime = orderTime;
        this.orderNumber = generateOrderNumber(); // Auto-create order number
    }

    // Generate unique order number (prefix + 6 random chars)
    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public LocalDateTime getOrderTime() { return orderTime; }
    public void setOrderTime(LocalDateTime orderTime) { this.orderTime = orderTime; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
