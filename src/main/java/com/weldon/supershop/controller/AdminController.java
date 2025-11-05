// ============================================
// File: AdminController.java
// Package: com.weldon.supershop.controller
// Description: Handles admin dashboard requests, reports, and data overview
// ============================================

package com.weldon.supershop.controller; // Define controller package

import com.weldon.supershop.model.Product; // Import product model
import com.weldon.supershop.model.Order; // Import order model
import com.weldon.supershop.repository.ProductRepository; // Import product repository
import com.weldon.supershop.repository.OrderRepository; // Import order repository
import org.springframework.beans.factory.annotation.Autowired; // For dependency injection
import org.springframework.stereotype.Controller; // Marks this class as controller
import org.springframework.ui.Model; // For passing data to HTML
import org.springframework.web.bind.annotation.GetMapping; // For GET routes

import java.util.List; // To handle lists of data

@Controller // Marks this class as a Spring MVC controller
public class AdminController {

    @Autowired // Injects product repository automatically
    private ProductRepository productRepository;

    @Autowired // Injects order repository automatically
    private OrderRepository orderRepository;

    // ===============================
    // Admin Dashboard
    // ===============================
    @GetMapping("/admin/dashboard") // Route for admin dashboard
    public String adminDashboard(Model model) {
        List<Product> products = productRepository.findAll(); // Fetch all products
        List<Order> orders = orderRepository.findAll(); // Fetch all orders

        // Calculate total sales (sum of all order totals)
        double totalSales = orders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();

        // Count total customers (unique email count)
        long totalCustomers = orders.stream()
                .map(Order::getCustomerEmail)
                .distinct()
                .count();

        // Add all attributes to model for display on dashboard
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalOrders", orders.size());
        model.addAttribute("totalProducts", products.size());
        model.addAttribute("totalCustomers", totalCustomers);

        return "admin_dashboard"; // Returns admin_dashboard.html
    }

    // ===============================
    // Reports Page
    // ===============================
    @GetMapping("/admin/reports") // Route for sales report
    public String viewReports(Model model) {
        List<Order> orders = orderRepository.findAll(); // Fetch all orders
        model.addAttribute("orders", orders); // Pass orders to view
        return "admin_reports"; // Returns admin_reports.html
    }
}
