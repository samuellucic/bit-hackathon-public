package hr.bithackathon.rental.domain.dto;

import hr.bithackathon.rental.domain.AppUser;
import hr.bithackathon.rental.domain.CommunityHomePlan;
import hr.bithackathon.rental.domain.Reservation;
import hr.bithackathon.rental.domain.ReservationType;

import java.time.Instant;

public record ReservationRequest(
        Long communityHomePlanId,
        Long customerId,
        String reason,
        Instant datetimeFrom,
        Instant datetimeTo,
        String bank,
        String iban,
        ReservationType type
) {
    public static Reservation toReservation(ReservationRequest reservationRequest, CommunityHomePlan communityHomePlan, AppUser customer) {
        return Reservation.builder()
                .communityHomePlan(communityHomePlan)
                .customer(customer)
                .reason(reservationRequest.reason())
                .datetimeFrom(reservationRequest.datetimeFrom)
                .datetimeTo(reservationRequest.datetimeTo)
                .bank(reservationRequest.bank())
                .iban(reservationRequest.iban())
                .type(reservationRequest.type())
                .build();
    }
}

