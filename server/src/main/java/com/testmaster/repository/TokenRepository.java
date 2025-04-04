package com.testmaster.repository;

import com.testmaster.model.TokenModel;
import com.testmaster.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<TokenModel, Long> {
    Optional<TokenModel> findByRefreshToken(String refreshToken);
    Optional<TokenModel> findByUser(UserModel user);
    void deleteByRefreshToken(String refreshToken);
}