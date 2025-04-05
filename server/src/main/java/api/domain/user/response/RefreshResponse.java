package api.domain.user.response;

import api.domain.user.JwtTokenPair;

public record RefreshResponse(JwtTokenPair data) {
}
