package hr.bithackathon.rental.domain.dto;

import java.time.Instant;

import hr.bithackathon.rental.domain.AppUser;
import hr.bithackathon.rental.domain.CommunityHomePlan;
import hr.bithackathon.rental.domain.Reservation;
import hr.bithackathon.rental.domain.ReservationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ReservationRequest(

    @NotNull
    AppUserRequest user,

    @Positive
    Long communityHomeId,

    @Positive
    Long customerId,

    @Size(min = 1, max = 255)
    String reason,

    @NotNull
    Instant datetimeFrom,

    @NotNull
    Instant datetimeTo,

    @NotNull
    ReservationType type,

    String bank,
    String iban

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

