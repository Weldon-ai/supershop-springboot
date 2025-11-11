package com.weldon.supershop.controller;

import com.weldon.supershop.model.Product;
import com.weldon.supershop.repository.ProductRepository;
import com.weldon.supershop.service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final ProductRepository productRepository;

    public CartController(CartService cartService, ProductRepository productRepository) {
        this.cartService = cartService;
        this.productRepository = productRepository;
    }

    // Add product to cart
    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addToCart(@PathVariable Long productId, HttpSession session) {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) return ResponseEntity.badRequest().body("Product not found");

        cartService.addProduct(session, product);
        return ResponseEntity.ok("Product added to cart");
    }

    // Remove product from cart
    @PostMapping("/remove/{productId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long productId, HttpSession session) {
        cartService.removeProduct(session, productId);
        return ResponseEntity.ok("Product removed from cart");
    }

    // Get all cart items
    @GetMapping("/items")
    public ResponseEntity<?> getCartItems(HttpSession session) {
        Map<Long, CartService.CartItem> cart = cartService.getCart(session);
        var response = cart.values().stream()
                .map(item -> Map.of(
                        "id", item.getProduct().getId(),
                        "name", item.getProduct().getName(),
                        "price", item.getProduct().getPrice(),
                        "quantity", item.getQuantity(),
                        "imageUrl", item.getProduct().getImageUrl() // Needed for cart.html
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // Get total cart price
    @GetMapping("/total")
    public ResponseEntity<Double> getTotal(HttpSession session) {
        return ResponseEntity.ok(cartService.getTotalPrice(session));
    }
}
