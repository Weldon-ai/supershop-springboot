package com.weldon.supershop.service;

import org.springframework.stereotype.Service;

// Marks this class as a Spring service (business logic)
@Service
public class MpesaService {

    // This method simulates M-Pesa STK Push payment
    public String simulatePayment(double amount, String phoneNumber) {
        // Simulate M-Pesa API response message
        return "M-Pesa STK Push simulated successfully for KES " + amount +
                " to phone " + phoneNumber + ".";
    }
}
