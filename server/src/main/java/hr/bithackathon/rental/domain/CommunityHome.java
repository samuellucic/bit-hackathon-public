package hr.bithackathon.rental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityHome {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String address;
    private String postalCode;
    private String city;
    private Double area;
    private Integer capacity;

    static CommunityHome dummy() {
        return CommunityHome.builder()
                .id(0L)
                .name("Dom 1")
                .address("Dom 1, BJ")
                .postalCode("10430")
                .city("BJ")
                .area(100.0)
                .capacity(25)
                .build();
    }
}
