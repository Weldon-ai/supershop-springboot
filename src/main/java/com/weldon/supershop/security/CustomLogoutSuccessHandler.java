package com.weldon.supershop.security;

import com.weldon.supershop.model.LoginLog;
import com.weldon.supershop.repository.LoginLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private LoginLogRepository loginLogRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();

            // Find the most recent login without a logoutTime
            List<LoginLog> logs = loginLogRepository.findAll();
            logs.stream()
                .filter(log -> log.getEmail().equals(email) && log.getLogoutTime() == null)
                .findFirst()
                .ifPresent(log -> {
                    log.setLogoutTime(LocalDateTime.now()); // <-- record logout
                    loginLogRepository.save(log);
                });
        }

        try {
            response.sendRedirect("/login?logout"); // redirect to login page
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
