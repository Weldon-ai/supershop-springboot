package com.weldon.supershop.service; // Package definition

import com.weldon.supershop.model.User; // Import User entity
 // Import Role entity
import com.weldon.supershop.repository.UserRepository; // Import UserRepository

import org.springframework.security.core.userdetails.UserDetailsService; // Core interface for loading user data
import org.springframework.security.core.userdetails.UserDetails; // Represents authenticated user
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Thrown if user not found
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Converts roles into Spring authorities
import org.springframework.stereotype.Service; // Marks as Spring service bean

import java.util.stream.Collectors; // For mapping roles to authorities

@Service // Marks as a Spring-managed service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // Dependency for fetching users from DB

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository; // Inject repository
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from database using username or throw exception if not found
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Return Spring Security user with username, password, and granted authorities
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), // username for login
                user.getPassword(), // password
                user.getRoles().stream() // Convert roles to Spring authorities
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()) // Collect into a list
        );
    }
}
