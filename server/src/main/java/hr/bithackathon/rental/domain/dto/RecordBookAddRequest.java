package hr.bithackathon.rental.domain.dto;

import hr.bithackathon.rental.domain.AppUser;
import hr.bithackathon.rental.domain.Contract;
import hr.bithackathon.rental.domain.RecordBook;
import hr.bithackathon.rental.domain.RecordBookStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RecordBookAddRequest(
    @NotNull
    @Positive
    Long contractId,

    @NotEmpty
    String stateBefore

) {

    public static RecordBook toRecordBook(RecordBookAddRequest recordBookAddRequest, Contract contract, AppUser custodian) {
        return RecordBook.builder()
                         .contract(contract)
                         .custodian(custodian)
                         .stateBefore(recordBookAddRequest.stateBefore())
                         .status(RecordBookStatus.CREATED)
                         .build();
    }

}

