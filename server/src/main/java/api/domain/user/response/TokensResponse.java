package api.domain.user.response;

import api.domain.user.JwtTokenPair;

public record TokensResponse(String accessToken, String refreshToken) {
}
