package com.testmaster.service.AuthService;

import com.testmasterapi.domain.user.JwtClaimNames;
import com.testmasterapi.domain.user.JwtTokenPair;
import com.auth0.jwt.HeaderParams;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.testmaster.config.properties.AuthProperties;
import com.testmaster.exeption.AuthException;
import com.testmaster.model.Token;
import com.testmaster.model.User.User;
import com.testmaster.repository.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {
    private final AuthProperties authProperties;

    private final TokenRepository tokenRepository;

    private Algorithm getHmacAlgorithm(String secret) {
        return Algorithm.HMAC256(secret);
    }

    private DecodedJWT validateToken(String token, String secret) {
        try {
            Algorithm algorithm = this.getHmacAlgorithm(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        }  catch (TokenExpiredException tokenExpiredException) {
            throw AuthException.jwtExpired(tokenExpiredException);
        }
    }

    @Override
    public String createJWT(User user, String secret, Duration expiration) {
        Instant now = Instant.now();
        return JWT.create()
                .withIssuedAt(now)
                .withExpiresAt(now.plus(expiration))
                .withJWTId(UUID.randomUUID().toString())
                .withClaim(HeaderParams.TYPE, "Bearer")
                .withSubject(user.getId().toString())
                .withClaim(JwtClaimNames.ID, user.getId().toString())
                .withClaim(JwtClaimNames.EMAIL, user.getEmail())
                .withClaim(JwtClaimNames.NAME, user.getName())
                .sign(this.getHmacAlgorithm(secret));
    }


    @Override
    public JwtTokenPair generateTokens(User user) {
        String accessToken = this.createJWT(user, authProperties.jwtAccessSecret(), Duration.ofDays(authProperties.jwtAccessLifeDuration()));
        String refreshToken = this.createJWT(user, authProperties.jwtRefreshSecret(), Duration.ofDays(authProperties.jwtRefreshLifeDuration()));

        return new JwtTokenPair(accessToken, refreshToken);
    }

    @Override
    public DecodedJWT validateAccessToken(String token) {
        return validateToken(token, authProperties.jwtAccessSecret());
    }

    @Override
    public DecodedJWT validateRefreshToken(String token) {
        return validateToken(token, authProperties.jwtRefreshSecret());
    }

    @Override
    public Token saveToken(User user, String refreshToken) {
        return tokenRepository.findByUser(user)
                .map(existing -> {
                    existing.setRefreshToken(refreshToken);
                    return tokenRepository.save(existing);
                })
                .orElseGet(() -> {
                    Token newToken = new Token();
                    newToken.setUser(user);
                    newToken.setRefreshToken(refreshToken);
                    return tokenRepository.save(newToken);
                });
    }


    @Override
    @Transactional
    public void removeToken(String refreshToken) {
        tokenRepository.deleteByRefreshToken(refreshToken);
    }

    @Override
    public Optional<Token> findToken(String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken);
    }

}
