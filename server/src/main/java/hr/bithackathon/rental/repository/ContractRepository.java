package hr.bithackathon.rental.repository;

import java.util.List;

import hr.bithackathon.rental.domain.Contract;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("SELECT c FROM Contract c WHERE c.reservation.customer.id = :customerId")
    List<Contract> findAllByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT c FROM Contract c WHERE c.reservation.customer.id = :customerId")
    List<Contract> findAllByCustomerId(@Param("customerId") Long customerId, Pageable pageable);

}
