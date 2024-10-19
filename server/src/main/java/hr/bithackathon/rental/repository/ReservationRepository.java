package hr.bithackathon.rental.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import hr.bithackathon.rental.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findAllByCustomerId(Long customerId, Pageable pageable);

    Optional<Reservation> findByIdAndCustomerId(Long id, Long customerId);

    List<Reservation> findAllByApprovedIsNullAndCommunityHomePlanId(Long communityHomePlanId);

    Optional<Reservation> findByKey(UUID key);

}
