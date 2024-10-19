package hr.bithackathon.rental.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import hr.bithackathon.rental.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findAllByCustomerId(Long customerId, Pageable pageable);

    Optional<Reservation> findByIdAndCustomerId(Long id, Long customerId);

    @Query("SELECT r FROM Reservation r " +
        "WHERE r.communityHomePlan.communityHome.id = :communityHomeId " +
        "AND r.approved = null " +
        "AND ((r.datetimeFrom BETWEEN :start AND :end) OR (r.datetimeTo BETWEEN :start AND :end))")
    List<Reservation> findAllNotApprovedForCommunityHomePlanAndBetween(@Param("communityHomeId") Long communityHomeId, Instant start, Instant end);

    Optional<Reservation> findByKey(UUID key);

}
