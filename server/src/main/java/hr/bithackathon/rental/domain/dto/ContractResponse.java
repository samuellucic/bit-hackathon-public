package hr.bithackathon.rental.domain.dto;

import hr.bithackathon.rental.domain.Contract;
import hr.bithackathon.rental.domain.ContractStatus;

public record ContractResponse(
    Long id,
    String customerFirstName,
    String customerLastName,
    Double lease,
    Double downPayment,
    Double utilities,
    Double total,
    Double vat,
    ContractStatus status
) {

    static public ContractResponse of(Contract contract) {
        return new ContractResponse(
            contract.getId(),
            contract.getReservation().getCustomer().getFirstName(),
            contract.getReservation().getCustomer().getLastName(),
            contract.getLease(),
            contract.getDownPayment(),
            contract.getUtilities(),
            contract.getTotal(),
            contract.getVat(),
            contract.getStatus()
        );
    }

}
