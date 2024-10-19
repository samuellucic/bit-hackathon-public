package hr.bithackathon.rental.domain.dto;

import hr.bithackathon.rental.security.JwtToken;

public record LoginResponse(
        JwtToken jwtToken,
        String authority) {
}
