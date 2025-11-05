package com.weldon.supershop.controller;

import com.weldon.supershop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller // Marks as web controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login") // Shows login page
    public String loginPage() {
        return "login"; // Returns login.html
    }

    @GetMapping("/register") // Shows register page
    public String registerPage() {
        return "register"; // Returns register.html
    }


    @PostMapping("/register")
public String registerUser(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String email,
        @RequestParam String fullName,
        @RequestParam(required = false) String phone,
        @RequestParam(required = false) String address
) {
    userService.registerUser(username, password, email, fullName, phone, address);
    return "redirect:/products"; // Redirect after registration
}

}
