package hr.bithackathon.rental.domain.dto;

import jakarta.validation.constraints.Email;

public record LoginRequest(

    @Email
    String email,

    String password

) {

}
