package com.weldon.supershop.controller; // Package declaration

// Import all models (Product, Order, etc.)
import com.weldon.supershop.model.*; 
// Import M-Pesa service for payment simulation
import com.weldon.supershop.service.MpesaService; 
// Import HttpSession to store cart per user
import jakarta.servlet.http.HttpSession; 
// Spring annotations and MVC utilities
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.*; 

// Import Java collections
import java.util.*; 

// Marks this class as a Spring MVC controller
@Controller 
// Base URL mapping for all endpoints in this controller
@RequestMapping("/order") 
public class OrderController {

    @Autowired // Injects the M-Pesa service
    private MpesaService mpesaService; 

    // GET endpoint to show checkout page
    @GetMapping("/checkout") 
    public String checkoutPage(HttpSession session, Model model) {
        // Retrieve cart from user session
        Object sessionCart = session.getAttribute("cart"); 
        Map<Integer, Product> cart = sessionCart instanceof Map ? (Map<Integer, Product>) sessionCart : new HashMap<>(); 

        // Calculate total price
        double total = cart.values().stream().mapToDouble(Product::getPrice).sum(); 

        // Add cart items to Thymeleaf model
        model.addAttribute("cart", cart.values()); 
        model.addAttribute("total", total); 

        // Return checkout.html template
        return "checkout"; 
    }

    // POST endpoint to place order
    @PostMapping("/place") 
    public String placeOrder(@RequestParam String name, @RequestParam String phone,
                             HttpSession session, Model model) {
        // Retrieve cart from session
        Object sessionCart = session.getAttribute("cart"); 
        Map<Integer, Product> cart = sessionCart instanceof Map ? (Map<Integer, Product>) sessionCart : new HashMap<>(); 

        // If cart is empty, redirect to cart page
        if (cart.isEmpty()) return "redirect:/cart"; 

        // Calculate total price of all products
        double total = cart.values().stream().mapToDouble(Product::getPrice).sum(); 

        // Simulate M-Pesa payment
        String response = mpesaService.simulatePayment(total, phone); 

        // Clear the cart after payment
        session.removeAttribute("cart"); 

        // Add receipt data to model
        model.addAttribute("message", response); 
        model.addAttribute("total", total); 
        model.addAttribute("name", name); 
        model.addAttribute("time", new Date()); // Include timestamp

        // Return receipt template
        return "receipt"; 
    }
}
