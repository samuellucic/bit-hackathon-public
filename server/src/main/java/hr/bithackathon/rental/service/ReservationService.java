package hr.bithackathon.rental.service;

import hr.bithackathon.rental.domain.AppUser;
import hr.bithackathon.rental.domain.CommunityHomePlan;
import hr.bithackathon.rental.domain.Reservation;
import hr.bithackathon.rental.domain.dto.ReservationRequest;
import hr.bithackathon.rental.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
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

    public Page<Reservation> getAllReservationsByUser(Long appUserId, int page, int size) {
        return reservationRepository.findAllByCustomerId(appUserId, PageRequest.of(page, size));
    }

    public Page<Reservation> getAllReservations(int page, int size) {
        return reservationRepository.findAll(PageRequest.of(page, size));
    }


    public Reservation getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow();
    }

    public void editReservation(Long reservationId, ReservationRequest request) {
        // TODO uncomment
        // CommunityHomePlan communityHomePlan = communityHomePlanService.getCommunityHomePlan(request.communityHomePlanId());
        CommunityHomePlan communityHomePlan = CommunityHomePlan.dummy();
        AppUser customer = appUserService.getCurrentAppUser();
        Reservation reservation = ReservationRequest.toReservation(request, communityHomePlan, customer);
        reservation.setId(reservationId);
        reservationRepository.save(reservation);
    }

    public void approveReservation(Long reservationId) {
        Reservation reservation = getReservation(reservationId);
        reservation.setApproved(true);
        reservationRepository.save(reservation);
    }
}
