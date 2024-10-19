package hr.bithackathon.rental.domain.dto;

import hr.bithackathon.rental.domain.RecordBook;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RecordBookResponse(
        Long contractId,
        Long custodianId,
        String stateBefore,
        String stateAfter,
        String damage,
        LocalDate inspectionDate

) {

    public static RecordBookResponse fromRecordBook(RecordBook recordBook) {
        return RecordBookResponse.builder()
                .contractId(recordBook.getContract().getId())
                .custodianId(recordBook.getCustodian().getId())
                .stateBefore(recordBook.getStateBefore())
                .stateAfter(recordBook.getStateAfter())
                .damage(recordBook.getDamage())
                .inspectionDate(recordBook.getInspectionDate())
                .build();
    }
}
