package hr.bithackathon.rental.domain;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(columnDefinition = "uuid")
    private UUID key;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private AppUser customer;

    @ManyToOne
    @JoinColumn(name = "community_home_plan_id")
    private CommunityHomePlan communityHomePlan;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private LocalDate creationDate;

    @Column(nullable = false)
    private Instant datetimeFrom;

    @Column(nullable = false)
    private Instant datetimeTo;

    @Column
    private String bank;

    @Column
    private String iban;

    @Column
    private Boolean approved;

    @Column(nullable = false)
    private ReservationType type;

}
