package com.weldon.supershop.service;

import org.springframework.stereotype.Service;

@Service
public class MpesaService {

    // This method simulates STK push. Integrate Safaricom Daraja API here.
    public boolean initiateStkPush(String phone, double amount, String accountReference) {
        // TODO: Replace this simulation with real API call to Daraja
        System.out.println("STK Push triggered for " + phone + " amount: " + amount);
        return true; // assume payment succeeds for now
    }
}
