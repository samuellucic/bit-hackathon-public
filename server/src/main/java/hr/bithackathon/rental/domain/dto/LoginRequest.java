package hr.bithackathon.rental.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(

    @NotNull
    @Email
    String email,

    @Size(min = 8, max = 255)
    String password

) {

}
