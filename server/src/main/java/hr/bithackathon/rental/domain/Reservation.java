package hr.bithackathon.rental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private AppUser appUser;
    @ManyToOne
    private CommunityHomePlan communityHomePlan;
    private String reason;
    private LocalDate creationDate;
    private Instant datetimeFrom;
    private Instant datetimeTo;
    private String bank;
    private String iban;
    private Boolean approved;
    private ReservationType type;

    public static Reservation dummy() {
        return Reservation.builder()
                .id(0L)
                .appUser(AppUser.dummy())
                .reason("reason")
                .communityHomePlan(CommunityHomePlan.dummy())
                .creationDate(LocalDate.now())
                .datetimeFrom(Instant.now().plus(Duration.ofDays(10)))
                .datetimeTo(Instant.now().plus(Duration.ofDays(10)).plus(Duration.ofHours(8)))
                .build();
    }
}
