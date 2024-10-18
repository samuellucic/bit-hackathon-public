package hr.bithackathon.rental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contract {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Reservation reservation;
    private Double lease;
    private Double downPayment;
    private Double amenities;
    private Double utilities;
    private Double total;
    private Double vat;
    private ContractStatus status;

    static Contract dummy() {
        return Contract.builder()
                .id(0L)
                .reservation(Reservation.dummy())
                .lease(1000.0)
                .downPayment(2.0)
                .amenities(100.0)
                .utilities(50.0)
                .total(2134.2)
                .vat(0.25)
                .status(ContractStatus.DUMMY)
                .build();
    }
}
