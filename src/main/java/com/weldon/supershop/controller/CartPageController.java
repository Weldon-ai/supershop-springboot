package com.weldon.supershop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartPageController {

    // Map /cart to Thymeleaf cart.html
    @GetMapping("/cart")
    public String cartPage() {
        return "cart"; // cart.html in src/main/resources/templates
    }
}
