package com.kronsoft.pharma.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, String> {
    Optional<AuthToken> findByRefreshToken(String rft);
    Optional<AuthToken> findByJwtToken(String jwt);
}
