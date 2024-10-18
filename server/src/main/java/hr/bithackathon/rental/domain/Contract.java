package hr.bithackathon.rental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @Column
    private Double lease;

    @Column
    private Double downPayment;

    @Column
    private Double amenities;

    @Column
    private Double utilities;

    @Column
    private Double total;

    @Column
    private Double vat;

    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    static Contract dummy() {
        return Contract.builder()
                       .id(0L)
                       .reservation(Reservation.dummy())
                       .lease(1000.0)
                       .downPayment(2.0)
                       .amenities(null)
                       .utilities(50.0)
                       .total(2134.2)
                       .vat(0.25)
                       .status(ContractStatus.DUMMY)
                       .build();
    }

}
