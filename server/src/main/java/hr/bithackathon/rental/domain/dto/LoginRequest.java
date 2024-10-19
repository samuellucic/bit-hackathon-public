package hr.bithackathon.rental.domain.dto;

public record LoginRequest(
    String email,
    String password) {

}
