package hr.bithackathon.rental.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @Column
    private String city;
    @Column
    private Double area;
    @Column
    private Integer capacity;
    @ManyToMany
    @JoinTable(
            name = "custodian_access_community_home",
            joinColumns = @JoinColumn(name = "community_home_id"),
            inverseJoinColumns = @JoinColumn(name = "custodian_id"))
    private List<AppUser> custodians;

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
