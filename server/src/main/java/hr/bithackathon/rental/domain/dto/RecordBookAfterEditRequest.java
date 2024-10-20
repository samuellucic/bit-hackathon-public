package hr.bithackathon.rental.domain.dto;

import jakarta.validation.constraints.NotEmpty;

public record RecordBookAfterEditRequest(

    @NotEmpty
    String stateAfter,

    @NotEmpty
    String damage
    
) {

}

