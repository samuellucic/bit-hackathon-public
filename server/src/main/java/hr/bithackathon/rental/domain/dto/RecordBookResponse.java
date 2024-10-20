package hr.bithackathon.rental.domain.dto;

import java.time.Instant;
import java.time.LocalDate;

import hr.bithackathon.rental.domain.RecordBook;
import hr.bithackathon.rental.domain.RecordBookStatus;
import lombok.Builder;

@Builder
public record RecordBookResponse(
    Long contractId,
    Long custodianId,
    Long recordBookId,
    String stateBefore,
    String stateAfter,
    String damage,
    LocalDate inspectionDate,
    String communityHomeName,
    Instant reservationStart,
    Instant reservationEnd,
    RecordBookStatus status,
    UserNameResponse custodian,
    UserNameResponse customer
) {

    public static RecordBookResponse from(RecordBook recordBook) {
        var customer = recordBook.getContract().getReservation().getCustomer();
        return RecordBookResponse.builder()
                                 .recordBookId(recordBook.getId())
                                 .contractId(recordBook.getContract().getId())
                                 .custodianId(recordBook.getCustodian().getId())
                                 .stateBefore(recordBook.getStateBefore())
                                 .stateAfter(recordBook.getStateAfter())
                                 .damage(recordBook.getDamage())
                                 .inspectionDate(recordBook.getInspectionDate())
                                 .communityHomeName(recordBook.getContract().getReservation().getCommunityHomePlan().getCommunityHome().getName())
                                 .reservationStart(recordBook.getContract().getReservation().getDatetimeFrom())
                                 .reservationEnd(recordBook.getContract().getReservation().getDatetimeTo())
                                 .status(recordBook.getStatus())
                                 .custodian(new UserNameResponse(recordBook.getCustodian().getFirstName(), recordBook.getCustodian().getLastName()))
                                 .customer(new UserNameResponse(customer.getFirstName(), customer.getLastName()))
                                 .build();
    }

}

