package com.example.shortlinks.security.token;

import com.example.shortlinks.security.token.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<UserToken, Long> {
}
