package hr.bithackathon.rental.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record HandleDownPaymentRequest(

    @NotNull
    @Positive
    Long recordBookId,

    @NotNull
    @Positive
    Boolean returnDownPayment

) {

}

