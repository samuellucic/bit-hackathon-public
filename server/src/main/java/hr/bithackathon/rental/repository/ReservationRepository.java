package hr.bithackathon.rental.repository;

import hr.bithackathon.rental.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findAllByCustomerId(Long customerId, Pageable pageable);

    Optional<Reservation> findByIdAndCustomerId(Long id, Long customerId);
}
