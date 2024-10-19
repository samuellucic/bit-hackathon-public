package hr.bithackathon.rental.service;

import hr.bithackathon.rental.domain.Reservation;
import hr.bithackathon.rental.domain.dto.ReservationRequest;
import hr.bithackathon.rental.exception.ErrorCode;
import hr.bithackathon.rental.exception.RentalException;
import hr.bithackathon.rental.repository.ReservationRepository;
import hr.bithackathon.rental.security.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CommunityHomePlanService communityHomePlanService;
    private final AppUserService appUserService;

    public Long createReservation(ReservationRequest request) {
        var communityHomePlan = communityHomePlanService.getCommunityHomePlan(request.communityHomePlanId());
        var customer = appUserService.getCurrentAppUser();
        var reservation = ReservationRequest.toReservation(request, communityHomePlan, customer);
        reservation.setCreationDate(LocalDate.now());
        reservation = reservationRepository.save(reservation);
        return reservation.getId();
    }

    public Page<Reservation> getAllReservationsByUser(Long appUserId, Pageable pageable) {
        return reservationRepository.findAllByCustomerId(appUserId, pageable);
    }

    public Page<Reservation> getAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable);
    }

    public Reservation getReservationForCurrentUser(Long reservationId) {
        return reservationRepository.findByIdAndCustomerId(reservationId, SecurityUtils.getCurrentUserDetails().getId())
                                    .orElseThrow(() -> new RentalException(ErrorCode.RESERVATION_NOT_FOUND));
    }

    public Reservation getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RentalException(ErrorCode.RESERVATION_NOT_FOUND));
    }


    public void editReservation(Long reservationId, ReservationRequest request) {
        var communityHomePlan = communityHomePlanService.getCommunityHomePlan(request.communityHomePlanId());
        var customer = appUserService.getCurrentAppUser();
        var reservation = ReservationRequest.toReservation(request, communityHomePlan, customer);
        reservation.setId(reservationId);
        reservationRepository.save(reservation);
    }

    public boolean approveReservation(Long reservationId, boolean approve) {
        var reservation = getReservation(reservationId);
        if (reservation.getApproved() != null) {
            throw new RentalException(ErrorCode.RESERVATION_HAS_APPROVAL_STATUS);
        }

        // TODO: Implement logic to see if it clashes with other reservations..
        // TODO: Deny others

        reservation.setApproved(approve);
        reservationRepository.save(reservation);

        return approve;
    }

}
