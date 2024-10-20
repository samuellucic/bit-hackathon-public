package hr.bithackathon.rental.domain.dto;

import jakarta.validation.constraints.NotEmpty;

public record RecordBookBeforeEditRequest(

    @NotEmpty
    String stateBefore

) {

}

