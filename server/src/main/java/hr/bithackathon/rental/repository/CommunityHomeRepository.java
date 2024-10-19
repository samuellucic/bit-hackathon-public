package hr.bithackathon.rental.repository;

import hr.bithackathon.rental.domain.CommunityHome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommunityHomeRepository extends JpaRepository<CommunityHome, Long> {

}
