package hr.bithackathon.rental.repository;

import hr.bithackathon.rental.domain.AppUser;
import hr.bithackathon.rental.domain.Contract;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("SELECT c FROM Contract c WHERE c.reservation.customer.id = :customerId")
    List<Contract> findAllByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT c FROM Contract c WHERE c.reservation.customer.id = :customerId")
    List<Contract> findAllByCustomerId(@Param("customerId") Long customerId, Pageable pageable);

    @Query("SELECT c FROM Contract c " +
        "WHERE c.reservation.communityHomePlan.communityHome.id = :communityHomeId " +
        "AND ((c.reservation.datetimeFrom BETWEEN :start AND :end) OR (c.reservation.datetimeTo BETWEEN :start AND :end))")
    List<Contract> findAllBetweenStartAndEnd(@Param("communityHomeId") Long communityHomeId, Instant start, Instant end);

    boolean existsContractByReservationId(Long reservationId);

    @Query("SELECT c FROM Contract c " +
            "WHERE c.id = :contractId " +
            "AND :custodian MEMBER OF c.reservation.communityHomePlan.communityHome.custodians")
    Optional<Contract> findByContractIdAndCustodianIdForCommunityHome(Long contractId, AppUser custodian);
}
