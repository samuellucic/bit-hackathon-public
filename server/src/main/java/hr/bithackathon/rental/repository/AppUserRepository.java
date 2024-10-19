package hr.bithackathon.rental.repository;

import java.util.Optional;

import hr.bithackathon.rental.domain.AppUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findOneByEmail(String email);

    @EntityGraph(attributePaths = "authority")
    Optional<AppUser> findOneWithAuthorityByEmail(String email);

}
