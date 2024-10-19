package hr.bithackathon.rental.controller;

import java.time.Duration;
import java.time.Instant;

import hr.bithackathon.rental.domain.dto.PaginationResponse;
import hr.bithackathon.rental.domain.dto.ReservationRequest;
import hr.bithackathon.rental.domain.dto.ReservationResponse;
import hr.bithackathon.rental.exception.ErrorCode;
import hr.bithackathon.rental.exception.RentalException;
import hr.bithackathon.rental.security.AuthoritiesConstants;
import hr.bithackathon.rental.security.SecurityUtils;
import hr.bithackathon.rental.security.aspect.HasAuthority;
import hr.bithackathon.rental.service.ContractService;
import hr.bithackathon.rental.service.ReservationService;
import hr.bithackathon.rental.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final ContractService contractService;

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequest reservationRequest) {
        if (SecurityUtils.isLoggedInCustomer() || SecurityUtils.isUserLoggedOut()) {
            var reservationStart = reservationRequest.datetimeFrom();
            if (reservationStart.isBefore(Instant.now().minus(Duration.ofDays(8)))) {
                throw new RentalException(ErrorCode.RESERVATION_PAST_DATE);
            }
        }

        var id = reservationService.createReservation(reservationRequest);
        return Util.getCreateResponse(id);
    }

    @GetMapping(value = "/users/{userId}/reservations", params = { "page", "size" })
    @HasAuthority({ AuthoritiesConstants.CUSTOMER, AuthoritiesConstants.OFFICIAL, AuthoritiesConstants.MAYOR })
    public PaginationResponse<ReservationResponse> getAllReservationsByUser(@PathVariable("userId") Long userId, Pageable pageable) {
        if (SecurityUtils.isLoggedInCustomer() && !userId.equals(SecurityUtils.getCurrentUserDetails().getId())) {
            throw new RentalException(ErrorCode.UNAUTHORIZED);
        }
        return PaginationResponse.fromPage(reservationService.getAllReservationsByUser(userId, pageable), ReservationResponse::fromReservation);
    }

    @GetMapping(value = "/reservations", params = { "page", "size" })
    @HasAuthority({ AuthoritiesConstants.OFFICIAL, AuthoritiesConstants.MAYOR })
    public PaginationResponse<ReservationResponse> getAllReservations(Pageable pageable, @RequestParam(required = false) Boolean approved) {
        return PaginationResponse.fromPage(reservationService.getAllReservations(pageable, approved), ReservationResponse::fromReservation);
    }

    @GetMapping("/reservations/{userId}")
    @HasAuthority({ AuthoritiesConstants.CUSTOMER, AuthoritiesConstants.OFFICIAL, AuthoritiesConstants.MAYOR })
    public ReservationResponse getReservation(@PathVariable("userId") Long userId) {
        if (SecurityUtils.isLoggedInCustomer() && !userId.equals(SecurityUtils.getCurrentUserDetails().getId())) {
            throw new RentalException(ErrorCode.UNAUTHORIZED);
        }
        return ReservationResponse.fromReservation(reservationService.getReservationForCurrentUser(userId));
    }

    @PutMapping("/reservations/{userId}")
    @HasAuthority({ AuthoritiesConstants.CUSTOMER, AuthoritiesConstants.OFFICIAL })
    public ResponseEntity<Void> editReservation(@PathVariable("userId") Long userId, ReservationRequest reservationRequest) {
        if (SecurityUtils.isLoggedInCustomer() && !userId.equals(SecurityUtils.getCurrentUserDetails().getId())) {
            throw new RentalException(ErrorCode.UNAUTHORIZED);
        }
        reservationService.editReservation(userId, reservationRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/reservations/{id}", params = { "approve" })
    @HasAuthority({ AuthoritiesConstants.OFFICIAL, AuthoritiesConstants.MAYOR })
    public void approveReservation(@PathVariable("id") Long reservationId, @RequestParam("approve") boolean approve) {
        var approved = reservationService.approveReservation(reservationId, approve);

        if (approved) {
            contractService.createContract(reservationId);
        }
    }

}
