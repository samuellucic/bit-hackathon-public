package hr.bithackathon.rental.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateContractRequest(

    @NotNull
    @Positive
    Long reservationId

) {

}
