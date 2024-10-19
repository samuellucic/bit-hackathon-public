package hr.bithackathon.rental.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class CommunityHome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String postalCode;
    //    @Column
    //    private String city;
    @Column
    private Double area;
    @Column
    private Integer capacity;

    public static CommunityHome dummy() {
        return CommunityHome.builder()
                            .id(1L)
                            .name("Dom 1")
                            .address("Dom 1, BJ")
                            .postalCode("10430")
                            //                .city("BJ")
                            .area(100.0)
                            .capacity(25)
                            .build();
    }

}
