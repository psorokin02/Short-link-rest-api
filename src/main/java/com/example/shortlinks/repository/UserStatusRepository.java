package com.example.shortlinks.repository;

import com.example.shortlinks.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus, Integer> {
}
