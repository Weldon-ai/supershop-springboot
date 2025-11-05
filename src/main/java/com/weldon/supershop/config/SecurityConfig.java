package com.weldon.supershop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Define which pages are public and which require login
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll() // Public pages
                .anyRequest().authenticated() // All other pages require login
            )
            // Configure login
            .formLogin(form -> form
                .loginPage("/login")            // Custom login page
                .loginProcessingUrl("/login")   // Handles POST /login automatically
                .usernameParameter("username")  // Username field name
                .passwordParameter("password")  // Password field name
                .defaultSuccessUrl("/products", true) // Redirect after successful login
                .permitAll()
            )
            // Configure logout
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()); // Optional: disable CSRF for testing

        return http.build();
    }

    // Password encoder for hashing
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

/*
package com.weldon.supershop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Allow all requests without authentication
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**", "/css/**", "/js/**").permitAll()
            )
            // Disable form login completely
            .formLogin().disable()
            // Disable logout functionality
            .logout().disable()
            // Disable CSRF for easy testing
            .csrf().disable();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
*/