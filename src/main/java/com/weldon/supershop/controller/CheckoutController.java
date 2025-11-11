package com.weldon.supershop.controller;

import com.weldon.supershop.model.Order;
import com.weldon.supershop.service.CartService;
import com.weldon.supershop.repository.OrderRepository;
import com.weldon.supershop.service.MpesaService; // Import the new service
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class CheckoutController {

    @Autowired
    private CartService cartService; // Inject CartService

    @Autowired
    private OrderRepository orderRepository; // Inject OrderRepository

    @Autowired
    private MpesaService mpesaService; // Inject MpesaService for payment simulation

    // Display checkout page
    @GetMapping("/checkout")
    public String checkoutPage(HttpSession session, Model model) {
        double total = cartService.getTotalPrice(session); // Get total cart amount
        model.addAttribute("total", total); // Pass total to Thymeleaf
        return "checkout"; // Render checkout.html
    }

    // Handle checkout form submission
    @PostMapping("/checkout")
    public String processCheckout(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("amount") double amount,
            HttpSession session,
            Model model
    ) {
        // 1️⃣ Simulate M-Pesa payment
        boolean paymentSuccess = mpesaService.initiateStkPush(phone, amount, "SuperShop Payment");

        if (!paymentSuccess) {
            model.addAttribute("error", "Payment failed! Please try again.");
            model.addAttribute("total", amount);
            return "checkout"; // Show checkout page again on failure
        }

        // 2️⃣ Payment succeeded → save order
        Order order = new Order();
        order.setCustomerName(name);
        order.setCustomerEmail(email);
        order.setTotalAmount(amount);
        order.setOrderDate(LocalDateTime.now());
        orderRepository.save(order);

        // 3️⃣ Clear cart
        cartService.clearCart(session);

        // 4️⃣ Redirect to receipt page
        model.addAttribute("order", order); // Pass order to receipt.html
        return "checkout_result"; // Render receipt.html
    }
}
