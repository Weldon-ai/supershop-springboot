// ============================================
// File: AdminController.java
// Package: com.weldon.supershop.controller
// Description: Handles admin dashboard requests, reports, and data overview
// ============================================

package com.weldon.supershop.controller;

import com.weldon.supershop.model.Product;
import com.weldon.supershop.model.Order;
import com.weldon.supershop.repository.ProductRepository;
import com.weldon.supershop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    // ===============================
    // Admin Dashboard
    // ===============================
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        List<Product> products = productRepository.findAll();
        List<Order> orders = orderRepository.findAll();

        double totalSales = orders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();

        long totalCustomers = orders.stream()
                .map(Order::getCustomerEmail)
                .distinct()
                .count();

        model.addAttribute("totalSales", totalSales);
        model.addAttribute("totalOrders", orders.size());
        model.addAttribute("totalProducts", products.size());
        model.addAttribute("totalCustomers", totalCustomers);

        return "admin_dashboard";
    }
@GetMapping("/admin/products")
public String viewProducts(Model model) {
    List<Product> allProducts = productRepository.findAll();

    // Sort products by ID descending to get recent products (assuming higher ID = newer)
    List<Product> recentProducts = allProducts.stream()
            .sorted((p1, p2) -> Long.compare(p2.getId(), p1.getId()))
            .limit(5) // Show 5 most recent products
            .collect(Collectors.toList());

    // Optional: simple recommendations (e.g., products over a certain price)
    List<Product> recommendedProducts = allProducts.stream()
            .filter(p -> p.getPrice() >= 1000) // example: expensive items
            .limit(5)
            .collect(Collectors.toList());

    model.addAttribute("products", allProducts);
    model.addAttribute("recentProducts", recentProducts);
    model.addAttribute("recommendedProducts", recommendedProducts);

    return "admin_products"; // your template
}

    // ===============================
    // Reports Page
    // ===============================
   /*  @GetMapping("/admin/reports")
    public String viewReports(Model model) {
        List<Order> orders = orderRepository.findAll();

        // Format order dates for display
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");

        List<Order> formattedOrders = orders.stream().map(order -> {
            if (order.getOrderDate() != null) {
                try {
                    order.setFormattedDate(order.getOrderDate().format(formatter));
                } catch (Exception e) {
                    order.setFormattedDate(order.getOrderDate().toString());
                }
            } else {
                order.setFormattedDate("N/A");
            }
            return order;
        }).collect(Collectors.toList());

        model.addAttribute("orders", formattedOrders);
        model.addAttribute("pageTitle", "Sales Reports - SuperShop");

        return "admin_reports";
    }*/
    @GetMapping("/admin/reports")
public String viewReports(Model model) {
    List<Order> orders = orderRepository.findAll();
    orders.forEach(o -> o.setFormattedDate(o.getOrderDate() != null ? 
        o.getOrderDate().format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")) : "N/A"));
    model.addAttribute("orders", orders);
    return "admin_reports"; // Must match template file name
}

}
