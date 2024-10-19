package hr.bithackathon.rental.controller;

import hr.bithackathon.rental.domain.Reservation;
import hr.bithackathon.rental.domain.dto.PaginationResponse;
import hr.bithackathon.rental.domain.dto.ReservationRequest;
import hr.bithackathon.rental.domain.dto.ReservationResponse;
import hr.bithackathon.rental.security.AuthoritiesConstants;
import hr.bithackathon.rental.security.aspect.HasAuthority;
import hr.bithackathon.rental.service.ReservationService;
import hr.bithackathon.rental.util.Util;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ReservationController {
    private ReservationService reservationService;

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    @HasAuthority(AuthoritiesConstants.CUSTOMER)
    public ResponseEntity<Void> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Long id = reservationService.createReservation(reservationRequest);
        return Util.getCreateResponse(id);
    }

    @GetMapping(value = "/user/{userId}/reservations", params = { "page", "size" })
    @HasAuthority(AuthoritiesConstants.CUSTOMER)
    public PaginationResponse<ReservationResponse> getAllReservationsByUser(@PathVariable("userId") Long userId,
                                                                            @RequestParam("page") int page,
                                                                            @RequestParam("size") int size) {
        return PaginationResponse.fromPage(reservationService.getAllReservationsByUser(userId, page, size), ReservationResponse::fromReservation);
    }

    @GetMapping(value = "/reservations", params = { "page", "size" })
    @HasAuthority(AuthoritiesConstants.OFFICIAL)
    public PaginationResponse<ReservationResponse> getAllReservations(@RequestParam("page") int page,
                                                                      @RequestParam("size") int size) {
        return PaginationResponse.fromPage(reservationService.getAllReservations(page, size), ReservationResponse::fromReservation);
    }

    @GetMapping("/reservations/{reservationId}")
    @HasAuthority({AuthoritiesConstants.CUSTOMER, AuthoritiesConstants.OFFICIAL})
    public ReservationResponse getReservation(@PathVariable("reservationId") Long reservationId) {
        return ReservationResponse.fromReservation(reservationService.getReservation(reservationId));
    }

    @PutMapping("/reservations/{id}")
    @HasAuthority(AuthoritiesConstants.CUSTOMER)
    public ResponseEntity<Void> editReservation(@PathVariable("id") Long id, ReservationRequest reservationRequest) {
        reservationService.editReservation(id, reservationRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reservations/{reservationId}/approve")
    @HasAuthority(AuthoritiesConstants.OFFICIAL)
    public void approveReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.approveReservation(reservationId);
    }


}
