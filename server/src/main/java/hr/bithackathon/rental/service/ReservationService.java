package hr.bithackathon.rental.service;

import java.time.LocalDate;
import java.util.UUID;

import hr.bithackathon.rental.domain.AppUser;
import hr.bithackathon.rental.domain.Reservation;
import hr.bithackathon.rental.domain.TimeRange;
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

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CommunityHomePlanService communityHomePlanService;
    private final AppUserService appUserService;
    private final NotificationService notificationService;

    public Long createReservation(ReservationRequest request) {
        AppUser appUser = null;
        if (SecurityUtils.isUserLoggedOut()) {
            appUser = appUserService.registerUser(request.user());
        } else {
            appUser = appUserService.getCurrentAppUser();
        }

        var communityHomePlan = communityHomePlanService.getCommunityHomePlan(request.communityHomePlanId());

        var reservation = ReservationRequest.toReservation(request, communityHomePlan, appUser);
        reservation.setCreationDate(LocalDate.now());
        reservation.setKey(UUID.randomUUID());
        reservation = reservationRepository.save(reservation);

        notificationService.sendUUIDReservationEmail(appUser.getEmail(), reservation.getKey());

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

    public Reservation getReservationByKey(UUID key) {
        return reservationRepository.findByKey(key)
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

        reservation.setApproved(approve);
        reservationRepository.save(reservation);

        // TODO: Implement logic to see if it clashes with other reservations..
        // TODO: Deny others if it clashes
        //        if (approve) {
        //            declineOverlaps(reservation);
        //        }

        return approve;
    }

    private void declineOverlaps(Reservation reservation) {
        var timeRange = new TimeRange(reservation.getDatetimeFrom(), reservation.getDatetimeTo());
        var reservations = reservationRepository.findAllByApprovedIsNullAndCommunityHomePlanId(reservation.getCommunityHomePlan().getId());

        //        reservations.stream()
        //                    .map(r -> new TimeRange(r.getDatetimeFrom(), r.getDatetimeTo()))
        //                    .filter(r -> r.overlaps(timeRange))
        //                    .forEach(r -> {
    }

    //    private boolean overlaps(TimeRange original, TimeRange other) {
    //        return (this.start.isBefore(other.end) && this.end.isAfter(other.start));
    //    }

}
