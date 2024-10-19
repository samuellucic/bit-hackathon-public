package hr.bithackathon.rental.service;

import hr.bithackathon.rental.domain.AppUser;
import hr.bithackathon.rental.domain.CommunityHomePlan;
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
        // TODO uncomment
        // CommunityHomePlan communityHomePlan = communityHomePlanService.getCommunityHomePlan(request.communityHomePlanId());
        CommunityHomePlan communityHomePlan = CommunityHomePlan.dummy();
        AppUser customer = appUserService.getCurrentAppUser();
        Reservation reservation = ReservationRequest.toReservation(request, communityHomePlan, customer);
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

    public Reservation getReservation(Long reservationId) {
        return reservationRepository.findByIdAndCustomerId(reservationId, SecurityUtils.getCurrentUserDetails().getId()).orElseThrow(() -> new RentalException(ErrorCode.RESERVATION_NOT_FOUND));
    }

    public Reservation editReservation(Long reservationId, ReservationRequest request) {
        // TODO uncomment
        // CommunityHomePlan communityHomePlan = communityHomePlanService.getCommunityHomePlan(request.communityHomePlanId());
        // TODO kako ovo u samuelovom sistemu
        CommunityHomePlan communityHomePlan = CommunityHomePlan.dummy();
        AppUser customer = appUserService.getCurrentAppUser();
        Reservation reservation = ReservationRequest.toReservation(request, communityHomePlan, customer);
        reservation.setId(reservationId);
        reservation = reservationRepository.save(reservation);
        return reservation;
    }

    public void approveReservation(Long reservationId, Boolean approve) {
        Reservation reservation = getReservation(reservationId);
        reservation.setApproved(approve);
        reservationRepository.save(reservation);
    }

}
