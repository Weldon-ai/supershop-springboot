package com.weldon.supershop.service;

import com.weldon.supershop.model.Product;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CartService {

    // Inner class representing a cart item
    public static class CartItem {
        private Product product; // The product
        private int quantity;    // Quantity of product

        public CartItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() { return product; }
        public void setProduct(Product product) { this.product = product; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

    private static final String CART_SESSION_KEY = "cart";

    // Get cart from session or create if missing
    @SuppressWarnings("unchecked")
    public Map<Long, CartItem> getCart(HttpSession session) {
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    // Add product to cart
    public void addProduct(HttpSession session, Product product) {
        Map<Long, CartItem> cart = getCart(session);
        Long productId = product.getId();

        if (cart.containsKey(productId)) {
            cart.get(productId).setQuantity(cart.get(productId).getQuantity() + 1);
        } else {
            cart.put(productId, new CartItem(product, 1));
        }

        session.setAttribute(CART_SESSION_KEY, cart);
    }

    // Remove product from cart
    public void removeProduct(HttpSession session, Long productId) {
        Map<Long, CartItem> cart = getCart(session);
        cart.remove(productId);
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    // Clear cart
    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }

    // Calculate total price
    public double getTotalPrice(HttpSession session) {
        return getCart(session).values().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}
