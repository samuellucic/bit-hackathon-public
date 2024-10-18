package hr.bithackathon.rental.repository;

import hr.bithackathon.rental.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByName(String authority);
}
