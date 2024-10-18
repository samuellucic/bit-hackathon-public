package hr.bithackathon.rental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityHomePlan {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private CommunityHome communityHome;
    private Double leaseCostPerHour;
    private Double downPayment;
    private Double amenitiesCostPerHour;
    private Double utilitiesCostPerHour;
    private Integer availableHourFrom;
    private Integer availableHourTo;

    static CommunityHomePlan dummy() {
        return CommunityHomePlan.builder()
                .id(0L)
                .communityHome(CommunityHome.dummy())
                .leaseCostPerHour(1000.0)
                .downPayment(2.0)
                .amenitiesCostPerHour(50.0)
                .build();
    }
}
