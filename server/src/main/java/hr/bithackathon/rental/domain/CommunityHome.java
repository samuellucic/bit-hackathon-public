package hr.bithackathon.rental.domain;

import jakarta.persistence.*;
import lombok.*;

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
