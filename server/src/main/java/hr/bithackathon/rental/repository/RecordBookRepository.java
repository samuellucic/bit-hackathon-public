package hr.bithackathon.rental.repository;

import hr.bithackathon.rental.domain.RecordBook;
import hr.bithackathon.rental.domain.RecordBookStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RecordBookRepository extends JpaRepository<RecordBook, Long> {
    @Query("SELECT r FROM RecordBook r WHERE r.id = :recordId AND r.contract.reservation.customer.id = :customerId AND r.status = :status")
    Optional<RecordBook> findByIdAndCustomerIdAndStatus(Long recordId, Long customerId, RecordBookStatus status);

    @Query("SELECT r FROM RecordBook r WHERE r.id = :recordId AND r.contract.reservation.customer.id = :customerId")
    Optional<RecordBook> findByIdAndCustomerId(Long recordId, Long customerId);

    Optional<RecordBook> findByIdAndStatus(Long recordId, RecordBookStatus status);

    Optional<RecordBook> findByIdAndCustodianId(Long recordId, Long custodianId);

    Page<RecordBook> findAllByCustodianId(Long custodianId, Pageable pageable);
}
