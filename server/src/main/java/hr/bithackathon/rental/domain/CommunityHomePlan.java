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
    @Column
    private Double leaseCostPerHour;
    @Column
    private Double downPayment;
    @Column
    private Double amenitiesCostPerHour;
    @Column
    private Double utilitiesCostPerHour;
    @Column
    private Integer availableHourFrom;
    @Column
    private Integer availableHourTo;

    public static CommunityHomePlan dummy() {
        return CommunityHomePlan.builder()
                                .id(1L)
                                .communityHome(CommunityHome.dummy())
                                .leaseCostPerHour(1000.0)
                                .downPayment(2.0)
                                .amenitiesCostPerHour(50.0)
                                .build();
    }

}
