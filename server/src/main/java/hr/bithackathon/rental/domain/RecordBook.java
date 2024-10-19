package hr.bithackathon.rental.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;
    @ManyToOne
    @JoinColumn(name = "custodian_id")
    private AppUser custodian;
    @Column
    private String stateBefore;
    @Column
    private String stateAfter;
    @Column
    private String damage;

    public static RecordBook dummy() {
        return RecordBook.builder()
                .id(0L)
                .contract(Contract.dummy())
                .custodian(AppUser.dummy())
                .stateBefore("before")
                .stateAfter("after")
                .damage("damage")
                .build();
    }
}
