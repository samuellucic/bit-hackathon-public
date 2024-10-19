package hr.bithackathon.rental.repository;

import hr.bithackathon.rental.domain.RecordBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RecordBookRepository extends JpaRepository<RecordBook, Long> {
    @Query("SELECT r FROM RecordBook r WHERE r.id = :recordId AND r.contract.reservation.customer.id = :customerId")
    Optional<RecordBook> findByIdAndCustomerId(Long recordId, Long customerId);
}
