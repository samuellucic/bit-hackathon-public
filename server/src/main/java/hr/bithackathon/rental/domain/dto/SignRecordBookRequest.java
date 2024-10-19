package hr.bithackathon.rental.domain.dto;

import jakarta.validation.constraints.Positive;

public record SignRecordBookRequest(

    @Positive
    Long recordBookId

) {

}

