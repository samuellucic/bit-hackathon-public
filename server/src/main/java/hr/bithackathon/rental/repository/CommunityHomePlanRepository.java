package hr.bithackathon.rental.repository;

import javax.swing.text.html.Option;

import java.util.Optional;

import hr.bithackathon.rental.domain.CommunityHomePlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityHomePlanRepository extends JpaRepository<CommunityHomePlan, Long> {

    Optional<CommunityHomePlan> findByCommunityHomeIdAndOrderBy

}
