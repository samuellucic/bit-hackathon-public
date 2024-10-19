package hr.bithackathon.rental.domain.dto;

import jakarta.validation.constraints.NotEmpty;

public record RecordBookEditRequest(

    @NotEmpty
    String stateAfter,

    @NotEmpty
    String damage
    
) {

}

