// Package name specifies where this controller belongs
package com.weldon.supershop.controller;

// Import the Product class to represent items in the cart
import com.weldon.supershop.model.Product;
// Import the ProductService for fetching products from the database
import com.weldon.supershop.service.ProductService;
// Import Spring and servlet utilities
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*; // Import for using Map and HashMap collections

// Marks this as a Spring MVC controller
@Controller
// Base mapping for all endpoints in this class: /cart
@RequestMapping("/cart")
public class CartController {

    // Automatically inject ProductService for fetching products
    @Autowired
    private ProductService productService;

    // Display the cart page with all products in the user's session cart
    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        // Suppress unchecked cast warning for session attribute
        @SuppressWarnings("unchecked")
        Map<Integer, Product> cart = (Map<Integer, Product>) session.getAttribute("cart"); // Get cart from session
        if (cart == null) cart = new HashMap<>(); // Initialize if cart is empty
        model.addAttribute("cart", cart.values()); // Add cart items to template
        double total = cart.values().stream().mapToDouble(Product::getPrice).sum(); // Calculate total
        model.addAttribute("total", total); // Add total to template
        return "cart"; // Render cart.html page
    }

    // Add product to cart by its ID
    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable int id, HttpSession session) {
        // Suppress unchecked cast warning for session attribute
        @SuppressWarnings("unchecked")
        Map<Integer, Product> cart = (Map<Integer, Product>) session.getAttribute("cart"); // Get cart from session
        if (cart == null) cart = new HashMap<>(); // Initialize if cart is empty
        Product product = productService.getProductById(Long.valueOf(id)); // Convert int to Long to match service
        cart.put(id, product); // Add product to cart
        session.setAttribute("cart", cart); // Save updated cart to session
        return "redirect:/cart"; // Redirect to view cart page
    }

    // Clear the entire cart
    @GetMapping("/clear")
    public String clearCart(HttpSession session) {
        session.removeAttribute("cart"); // Remove cart from session
        return "redirect:/cart"; // Redirect to empty cart page
    }
}
