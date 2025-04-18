package com.testmaster.repository;

import com.testmaster.model.Token;
import com.testmaster.model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByRefreshToken(String refreshToken);
    Optional<Token> findByUser(User user);
    void deleteByRefreshToken(String refreshToken);
}