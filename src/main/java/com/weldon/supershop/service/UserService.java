package com.weldon.supershop.service;

import com.weldon.supershop.model.Role;
import com.weldon.supershop.model.User;
import com.weldon.supershop.repository.RoleRepository;
import com.weldon.supershop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections; // Used to create singleton sets

@Service // Marks this class as a service component
public class UserService {

    @Autowired // Auto-inject repository dependencies
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Encrypts passwords

    // Register a new user
    public void registerUser(String username, String password) {
        User user = new User(); // Create a new user instance
        user.setUsername(username); // Set username
        user.setPassword(passwordEncoder.encode(password)); // Encrypt and set password

        // Assign default role USER to every new registration
        Role role = roleRepository.findByName("USER");
        user.setRoles(Collections.singleton(role));

        // Save user to database
        userRepository.save(user);
    }
    public void registerUser(String fullName, String username, String email, String password) {
    User user = new User();
    user.setFullName(fullName); // add fullName
    user.setUsername(username);
    user.setEmail(email); // must be set
    user.setPassword(passwordEncoder.encode(password));

    Role role = roleRepository.findByName("USER");
    user.setRoles(Collections.singleton(role));

    userRepository.save(user);
}

    public void registerUser(String username, String password, String email, String fullName, String phone, String address) {
    User user = new User();
    user.setUsername(username);
    user.setPassword(passwordEncoder.encode(password));
    user.setEmail(email);
    user.setFullName(fullName);
    user.setPhone(phone);
    user.setAddress(address);

    Role role = roleRepository.findByName("USER");
    user.setRoles(Collections.singleton(role));

    userRepository.save(user);
}

}
