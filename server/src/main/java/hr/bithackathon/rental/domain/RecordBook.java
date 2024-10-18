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
    @GeneratedValue
    private Long id;

    @OneToOne
    private Contract contract;
    @ManyToOne
    private AppUser custodian;
    private String stateBefore;
    private String stateAfter;
    private String damage;

    static RecordBook dummy() {
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
