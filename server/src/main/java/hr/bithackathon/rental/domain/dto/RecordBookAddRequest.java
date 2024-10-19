package hr.bithackathon.rental.domain.dto;

import hr.bithackathon.rental.domain.AppUser;
import hr.bithackathon.rental.domain.Contract;
import hr.bithackathon.rental.domain.RecordBook;

public record RecordBookAddRequest(
        Long contractId,
        String stateBefore
) {

    public static RecordBook toRecordBook(RecordBookAddRequest recordBookAddRequest, Contract contract, AppUser custodian) {
        return RecordBook.builder()
                .contract(contract)
                .custodian(custodian)
                .stateBefore(recordBookAddRequest.stateBefore())
                .build();
    }
}

