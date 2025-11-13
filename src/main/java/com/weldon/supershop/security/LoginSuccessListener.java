package com.weldon.supershop.security;

import com.weldon.supershop.model.LoginLog;
import com.weldon.supershop.repository.LoginLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Listens for successful authentication events and logs user login details.
 */
@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private LoginLogRepository loginLogRepository;

    /**
     * Called when a user successfully logs in.
     * @param event the AuthenticationSuccessEvent (never null)
     */
    @Override
    public void onApplicationEvent(@NonNull AuthenticationSuccessEvent event) {
        if (event.getAuthentication() != null && event.getAuthentication().getName() != null) {
            String email = event.getAuthentication().getName(); // usually the username/email
            LoginLog log = new LoginLog(email, LocalDateTime.now());
            loginLogRepository.save(log);
        }
    }
}
