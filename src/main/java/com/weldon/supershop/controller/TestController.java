
package com.weldon.supershop.controller;

import com.weldon.supershop.model.User;
import com.weldon.supershop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/save")
    public String saveUser() {
        User user = new User();
        user.setFullName("vivian");
        user.setPassword("password123");
        user.setEmail("vivian@clothesstore.com");
        userRepository.save(user);
        return "User saved successfully!";
    }
}
