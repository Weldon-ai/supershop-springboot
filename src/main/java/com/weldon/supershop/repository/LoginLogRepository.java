package com.weldon.supershop.repository;

import com.weldon.supershop.model.LoginLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoginLogRepository extends JpaRepository<LoginLog, Long> {

    @Query("SELECT l FROM LoginLog l WHERE l.logoutTime IS NULL")
    List<LoginLog> findActiveUsers();
}
