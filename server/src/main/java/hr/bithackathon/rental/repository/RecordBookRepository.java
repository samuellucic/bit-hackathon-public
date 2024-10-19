package hr.bithackathon.rental.repository;

import hr.bithackathon.rental.domain.RecordBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordBookRepository extends JpaRepository<RecordBook, Long> {

}
