package api.domain.user;

public record JwtTokenPair(
        String accessToken,
        String refreshToken
) {}
