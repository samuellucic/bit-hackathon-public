package hr.bithackathon.rental.repository;

import hr.bithackathon.rental.domain.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {

}
