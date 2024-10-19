package hr.bithackathon.rental.controller;

import hr.bithackathon.rental.domain.dto.PaginationResponse;
import hr.bithackathon.rental.domain.dto.ReservationRequest;
import hr.bithackathon.rental.domain.dto.ReservationResponse;
import hr.bithackathon.rental.security.AuthoritiesConstants;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final ContractService contractService;

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    @HasAuthority(AuthoritiesConstants.CUSTOMER)
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Long id = reservationService.createReservation(reservationRequest);
        return Util.getCreateResponse(id);
    }

    @GetMapping(value = "/users/{userId}/reservations", params = { "page", "size" })
    @HasAuthority(AuthoritiesConstants.CUSTOMER)
    public PaginationResponse<ReservationResponse> getAllReservationsByUser(@PathVariable("userId") Long userId,
                                                                            Pageable pageable) {
        return PaginationResponse.fromPage(reservationService.getAllReservationsByUser(userId, pageable), ReservationResponse::fromReservation);
    }

    @GetMapping(value = "/reservations", params = { "page", "size" })
    @HasAuthority(AuthoritiesConstants.OFFICIAL)
    public PaginationResponse<ReservationResponse> getAllReservations(Pageable pageable) {
        return PaginationResponse.fromPage(reservationService.getAllReservations(pageable), ReservationResponse::fromReservation);
    }

    @GetMapping("/reservations/{id}")
    @HasAuthority({ AuthoritiesConstants.CUSTOMER, AuthoritiesConstants.OFFICIAL })
    public ReservationResponse getReservation(@PathVariable("id") Long id) {
        return ReservationResponse.fromReservation(reservationService.getReservation(id));
    }

    @PutMapping("/reservations/{id}")
    @HasAuthority(AuthoritiesConstants.CUSTOMER)
    public ResponseEntity<Void> editReservation(@PathVariable("id") Long id, ReservationRequest reservationRequest) {
        reservationService.editReservation(id, reservationRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/reservations/{id}", params = { "approve" })
    @HasAuthority(AuthoritiesConstants.OFFICIAL)
    public void approveReservation(@PathVariable("id") Long reservationId) {
        reservationService.approveReservation(reservationId);
        //TODO: notify about successful reservation approval
        //TODO: Disapprove other reservations for the same time
        contractService.createContract(reservationId);
    }

}
