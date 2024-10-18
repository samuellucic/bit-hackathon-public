package hr.bithackathon.rental.domain.dto;

import hr.bithackathon.rental.domain.Reservation;
import hr.bithackathon.rental.domain.ReservationType;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;

@Builder
public record ReservationResponse (
        Long reservationId,
        Long customerId,
        Long communityHomePlanId,
        LocalDate creationDate,
        String reason,
        Instant datetimeFrom,
        Instant datetimeTo,
        String bank,
        String iban,
        Boolean approved,
        ReservationType type
) {

    public static ReservationResponse fromReservation(Reservation reservation) {
        return ReservationResponse.builder()
                .reservationId(reservation.getId())
                .customerId(reservation.getCustomer().getId())
                .communityHomePlanId(reservation.getCommunityHomePlan().getId())
                .creationDate(reservation.getCreationDate())
                .reason(reservation.getReason())
                .datetimeFrom(reservation.getDatetimeFrom())
                .datetimeTo(reservation.getDatetimeTo())
                .bank(reservation.getBank())
                .iban(reservation.getIban())
                .approved(reservation.getApproved())
                .type(reservation.getType())
                .build();
    }
}
