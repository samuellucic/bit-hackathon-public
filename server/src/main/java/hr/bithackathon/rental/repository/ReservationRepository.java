package hr.bithackathon.rental.repository;

import hr.bithackathon.rental.domain.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Page<Reservation> findAllByCustomerId(Long customer_id, Pageable pageable);

}
