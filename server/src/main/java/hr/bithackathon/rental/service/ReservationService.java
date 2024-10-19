package hr.bithackathon.rental.service;

import java.time.Duration;
import java.time.Instant;
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
        checkPassword(request.user().password());

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

    public Page<Reservation> getAllReservations(Pageable pageable, Boolean approved) {
        return reservationRepository.findAllByApproved(approved, pageable);
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

        if (approve) {
            declineOverlaps(reservation);
        }

        return approve;
    }

    private void declineOverlaps(Reservation reservation) {
        var startPadded = reservation.getDatetimeFrom().minus(Duration.ofHours(1));
        var endPadded = reservation.getDatetimeTo().plus(Duration.ofHours(1));
        var communityHomePlanId = reservation.getCommunityHomePlan().getId();
        var reservations = reservationRepository.findAllNotApprovedForCommunityHomePlanAndBetween(communityHomePlanId,
                                                                                                  startPadded, endPadded);

        reservations.stream()
                    .filter(r -> this.isTouchingEnds(new TimeRange(r.getDatetimeFrom(), r.getDatetimeTo()), startPadded,
                                                     endPadded))
                    .forEach(r -> {
                        r.setApproved(false);
                        notificationService.notifyReservationDeclinedEmail(r.getId(), r.getCustomer().getEmail());
                        reservationRepository.save(r);
                    });
    }

    private boolean isTouchingEnds(TimeRange range, Instant start, Instant end) {
        return range.end().equals(start) || range.start().equals(end);
    }

    private void checkPassword(String password) {
        if (password.length() < 8) {
            throw new RentalException(ErrorCode.PASSWORD_TOO_WEAK);
        }

        if (!password.matches(".*[A-Z].*")) {
            throw new RentalException(ErrorCode.PASSWORD_TOO_WEAK);
        }

        if (!password.matches(".*[a-z].*")) {
            throw new RentalException(ErrorCode.PASSWORD_TOO_WEAK);
        }

        if (!password.matches(".*[0-9].*")) {
            throw new RentalException(ErrorCode.PASSWORD_TOO_WEAK);
        }
    }

}