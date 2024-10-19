package hr.bithackathon.rental.controller;

import java.util.UUID;

import hr.bithackathon.rental.domain.dto.ReservationResponse;
import hr.bithackathon.rental.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/action/reservations")
@RequiredArgsConstructor
public class ReservationActionController {

    private final ReservationService reservationService;

    @GetMapping("/view/{uuid}")
    public ReservationResponse viewReservation(@PathVariable("uuid") UUID uuid) {
        var reservation = reservationService.getReservationByKey(uuid);
        return ReservationResponse.fromReservation(reservation);
    }

}
