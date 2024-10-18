package hr.bithackathon.rental.service;

import java.time.Duration;

import hr.bithackathon.rental.config.FreeReservationConfiguration;
import hr.bithackathon.rental.domain.Contract;
import hr.bithackathon.rental.domain.ContractStatus;
import hr.bithackathon.rental.domain.Reservation;
import hr.bithackathon.rental.repository.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final FreeReservationConfiguration freeReservationConfiguration;
    private final ContractRepository contractRepository;

    static final Double VAT = 0.25;

    public Long createContract(Long reservationId) {
        // TODO: Call repository
        var reservation = Reservation.dummy();

        if (freeReservationConfiguration.isFreeReservationType(reservation.getType())) {
            return createFreeContract(reservation);
        }

        var communityHomePlan = reservation.getCommunityHomePlan();
        var numberOfHours = Duration.between(reservation.getDatetimeFrom(), reservation.getDatetimeTo()).toHours();

        var lease = communityHomePlan.getLeaseCostPerHour() * numberOfHours;
        var downPayment = communityHomePlan.getDownPayment();
        var utilities = communityHomePlan.getUtilitiesCostPerHour() * numberOfHours;
        var vat = (lease + utilities + downPayment) * VAT;
        var total = lease + utilities + downPayment + vat;

        var contract = Contract.builder()
                               .reservation(reservation)
                               .lease(lease)
                               .downPayment(downPayment)
                               .utilities(utilities)
                               .total(total)
                               .vat(vat)
                               .status(ContractStatus.CREATED)
                               .build();

        return contractRepository.save(contract).getId();
    }

    private Long createFreeContract(Reservation reservation) {
        var contract = Contract.builder()
                               .reservation(reservation)
                               .lease(0.0)
                               .downPayment(0.0)
                               .utilities(0.0)
                               .total(0.0)
                               .vat(0.0)
                               .status(ContractStatus.CREATED)
                               .build();

        return contractRepository.save(contract).getId();
    }

    public Long dummy() {
        var contract = Contract.builder()
                               .lease(0.0)
                               .downPayment(0.0)
                               .utilities(0.0)
                               .total(0.0)
                               .vat(0.0)
                               .status(ContractStatus.CREATED)
                               .build();

        var fromDb = contractRepository.save(contract);
        var id = fromDb.getId();

        return id;
    }

}
