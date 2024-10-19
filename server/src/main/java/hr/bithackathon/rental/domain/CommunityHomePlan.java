package hr.bithackathon.rental.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class CommunityHomePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "community_home_id")
    private CommunityHome communityHome;

    @Column(nullable = false)
    private Double leaseCostPerHour;

    @Column(nullable = false)
    private Double downPayment;

    @Column(nullable = false)
    private Double amenitiesCostPerHour;

    @Column(nullable = false)
    private Double utilitiesCostPerHour;

    @Column
    private Integer availableHourFrom;

    @Column
    private Integer availableHourTo;

}
