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
import com.testmaster.mapper.UserMapper;
import com.testmaster.model.TokenModel;
import com.testmaster.model.UserModel;
import com.testmaster.repository.TokenRepository;
import com.testmaster.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements TokenAuthService, UserAuthService {
    private final AuthProperties authProperties;

    private final UserRepository userRepository;

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
    public String login(String token) {
        DecodedJWT decodedJWT = validateAccessToken(token);
        String email = decodedJWT.getClaim(JwtClaimNames.EMAIL).asString();

        UserModel user = userRepository
                .findByEmail(email)
                .orElseGet(() -> userRepository.save(UserMapper.map(decodedJWT)));

        if (user.getDeleted()) {
            throw new AuthException("Пользователь с " + email + " удален!");
        }

        return token;
    }

    @Override
    public String createJWT(UserModel user, String secret, Duration expiration) {
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
    public JwtTokenPair generateTokens(UserModel user) {
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
    public TokenModel saveToken(UserModel user, String refreshToken) {
        return tokenRepository.findByUser(user)
                .map(existing -> {
                    existing.setRefreshToken(refreshToken);
                    return tokenRepository.save(existing);
                })
                .orElseGet(() -> {
                    TokenModel newToken = new TokenModel();
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
    public Optional<TokenModel> findToken(String refreshToken) {
        return tokenRepository.findByRefreshToken(refreshToken);
    }

}
