package com.weldon.supershop.model;

import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
public class LoginLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;

    @Transient // Not stored in DB, used for display
    private String sessionDuration;

    public LoginLog() {}

    public LoginLog(String email, LocalDateTime loginTime) {
        this.email = email;
        this.loginTime = loginTime;
    }

    // ---------------- Getters and Setters ----------------
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDateTime getLoginTime() { return loginTime; }
    public void setLoginTime(LocalDateTime loginTime) { this.loginTime = loginTime; }
    public LocalDateTime getLogoutTime() { return logoutTime; }
    public void setLogoutTime(LocalDateTime logoutTime) { this.logoutTime = logoutTime; }
    public String getSessionDuration() { return sessionDuration; }
    public void setSessionDuration(String sessionDuration) { this.sessionDuration = sessionDuration; }

    // ---------------- Calculate Session Duration ----------------
    public void calculateSessionDuration() {
        if (loginTime == null) {
            this.sessionDuration = "N/A";
            return;
        }

        LocalDateTime endTime = (logoutTime != null) ? logoutTime : LocalDateTime.now();
        Duration duration = Duration.between(loginTime, endTime);
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        this.sessionDuration = String.format("%02dh:%02dm:%02ds", hours, minutes, seconds);
    }
}
