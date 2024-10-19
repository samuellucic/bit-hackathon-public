package hr.bithackathon.rental.domain;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private AppUser customer;
    @ManyToOne
    @JoinColumn(name = "community_home_plan_id")
    private CommunityHomePlan communityHomePlan;
    @Column
    private String reason;
    @Column
    private LocalDate creationDate;
    @Column
    private Instant datetimeFrom;
    @Column
    private Instant datetimeTo;
    @Column
    private String bank;
    @Column
    private String iban;
    @Column
    private Boolean approved;
    @Column
    private ReservationType type;

    public static Reservation dummy() {
        return Reservation.builder()
                .id(0L)
                .customer(AppUser.dummy())
                .reason("reason")
                .communityHomePlan(CommunityHomePlan.dummy())
                .creationDate(LocalDate.now())
                .datetimeFrom(Instant.now().plus(Duration.ofDays(10)))
                .datetimeTo(Instant.now().plus(Duration.ofDays(10)).plus(Duration.ofHours(8)))
                .build();
    }
}
